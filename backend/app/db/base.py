from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import DeclarativeBase
from sqlalchemy import Column, String, DateTime, Integer, Boolean
import uuid
from datetime import datetime

class Base(DeclarativeBase):
    pass

class SyncBaseMixin:
    """
    Base fields required for offline-first sync architecture.
    """
    uuid = Column(String(36), primary_key=True, default=lambda: str(uuid.uuid4()))
    server_id = Column(String(36), nullable=True)
    created_at = Column(DateTime, default=datetime.utcnow, nullable=False)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow, nullable=False, index=True)
    version = Column(Integer, default=1, nullable=False, index=True)
    is_deleted = Column(Boolean, default=False, nullable=False)
