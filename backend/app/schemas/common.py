from pydantic import BaseModel
from typing import Optional
from datetime import datetime

class SyncBaseSchema(BaseModel):
    uuid: str
    server_id: Optional[str] = None
    created_at: datetime
    updated_at: datetime
    version: int
    is_deleted: bool

    class Config:
        from_attributes = True
