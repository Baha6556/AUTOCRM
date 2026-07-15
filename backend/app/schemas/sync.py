from pydantic import BaseModel
from typing import List, Dict, Any, Optional

class SyncOperationSchema(BaseModel):
    operation_id: str
    entity_type: str
    entity_id: str
    operation_type: str  # CREATE, UPDATE, DELETE
    data: Optional[Dict[str, Any]] = None
    version: int

class SyncPushRequest(BaseModel):
    operations: List[SyncOperationSchema]

class SyncConflictResponse(BaseModel):
    status: str = "CONFLICT"
    server_version: int
    server_data: Dict[str, Any]

class SyncPullResponse(BaseModel):
    updated_entities: List[Dict[str, Any]]
    deleted_entities: List[Dict[str, Any]]
    server_timestamp: int
