import time
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select
from sqlalchemy import update
from fastapi import HTTPException
from typing import Dict, Any

from app.db.models import Car, Expense, SyncOperation, AuditLog
from app.schemas.sync import SyncOperationSchema

# Map entity types to their SQLAlchemy models
ENTITY_MODEL_MAP = {
    "Car": Car,
    "Expense": Expense,
    # Add Suppliers, Clients, etc.
}

class SyncEngine:
    def __init__(self, db: AsyncSession):
        self.db = db

    async def process_push_operation(self, op: SyncOperationSchema, user_id: str):
        # 1. Identity Check (Idempotency)
        result = await self.db.execute(select(SyncOperation).where(SyncOperation.operation_id == op.operation_id))
        existing_op = result.scalars().first()
        if existing_op:
            return  # Idempotent, already processed
            
        model = ENTITY_MODEL_MAP.get(op.entity_type)
        if not model:
            raise HTTPException(status_code=400, detail=f"Unknown entity type: {op.entity_type}")
            
        now = int(time.time() * 1000)

        # Retrieve existing entity
        entity_result = await self.db.execute(select(model).where(model.uuid == op.entity_id))
        entity = entity_result.scalars().first()
        
        # 2. Version Check
        if entity:
            if op.version < entity.version:
                # Conflict!
                raise HTTPException(
                    status_code=409,
                    detail={
                        "status": "CONFLICT",
                        "server_version": entity.version,
                        "server_data": {c.name: getattr(entity, c.name) for c in model.__table__.columns}
                    }
                )

        # 3. Apply Change
        if op.operation_type in ["CREATE", "UPDATE"]:
            if not entity:
                # Create new
                entity_data = op.data.copy()
                entity_data["uuid"] = op.entity_id
                entity_data["version"] = 1
                entity_data["created_at"] = now
                entity_data["updated_at"] = now
                entity_data["is_deleted"] = False
                
                new_entity = model(**entity_data)
                self.db.add(new_entity)
            else:
                # Update existing
                for key, value in op.data.items():
                    if hasattr(entity, key) and key not in ["uuid", "version", "created_at", "updated_at"]:
                        setattr(entity, key, value)
                entity.version += 1
                entity.updated_at = now
                
        elif op.operation_type == "DELETE":
            if entity:
                entity.is_deleted = True
                entity.version += 1
                entity.updated_at = now

        # 4. Side Effects: Audit Log & Sync Operation
        audit = AuditLog(
            uuid=f"audit_{op.operation_id}",
            entity_type=op.entity_type,
            entity_id=op.entity_id,
            action_type=op.operation_type,
            old_value=None, # In a full impl, capture old state
            new_value=op.data,
            timestamp=now,
            user_id=user_id
        )
        self.db.add(audit)
        
        sync_op = SyncOperation(
            operation_id=op.operation_id,
            entity_type=op.entity_type,
            entity_id=op.entity_id,
            operation_type=op.operation_type,
            payload_json=op.data,
            status="APPLIED",
            created_at=now,
            processed_at=now
        )
        self.db.add(sync_op)
        
        # Wait for route to commit transaction
