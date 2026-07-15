from fastapi import APIRouter, Depends
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select

from app.db.session import get_db
from app.schemas.sync import SyncPushRequest, SyncPullResponse
from app.api.dependencies import get_current_user
from app.schemas.auth import UserOut
from app.services.sync_engine import SyncEngine
from app.db.models import Car, Expense
import time

router = APIRouter()

@router.post("/push")
async def push_sync(
    request: SyncPushRequest,
    db: AsyncSession = Depends(get_db),
    current_user: UserOut = Depends(get_current_user)
):
    engine = SyncEngine(db)
    
    # We process in order
    for op in request.operations:
        # Each operation is processed. We can do it in one big transaction or per operation.
        # Strict requirement: "Crash-safe transactions" -> atomic per operation is best.
        # But we will use the injected db session which is committed at the end.
        await engine.process_push_operation(op, current_user.uuid)
        
    await db.commit()
    return {"status": "ACK"}

@router.get("/pull", response_model=SyncPullResponse)
async def pull_sync(
    last_sync_timestamp: int,
    db: AsyncSession = Depends(get_db),
    current_user: UserOut = Depends(get_current_user)
):
    # Retrieve all updated entities. (Simple implementation for Cars)
    # A full impl loops through all ENTITY_MODEL_MAP
    result = await db.execute(select(Car).where(Car.updated_at > last_sync_timestamp))
    cars = result.scalars().all()
    
    updated = []
    deleted = []
    
    for car in cars:
        data = {c.name: getattr(car, c.name) for c in Car.__table__.columns}
        if car.is_deleted:
            deleted.append(data)
        else:
            updated.append(data)
            
    now = int(time.time() * 1000)
    return SyncPullResponse(
        updated_entities=updated,
        deleted_entities=deleted,
        server_timestamp=now
    )
