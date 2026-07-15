from sqlalchemy import Column, String, Float, Integer, ForeignKey, JSON
from sqlalchemy.orm import relationship
from app.db.base import Base, SyncBaseMixin

class User(Base):
    __tablename__ = "users"
    
    uuid = Column(String(36), primary_key=True)
    email = Column(String(255), unique=True, index=True, nullable=False)
    hashed_password = Column(String(255), nullable=False)
    full_name = Column(String(255))
    role = Column(String(50), default="user")

class Supplier(Base, SyncBaseMixin):
    __tablename__ = "suppliers"
    
    company_name = Column(String(255), nullable=False)
    contact_person = Column(String(255))
    phone = Column(String(50))
    country = Column(String(100), nullable=False)
    rating = Column(Float, default=0.0)
    
    cars = relationship("Car", back_populates="supplier")

class Client(Base, SyncBaseMixin):
    __tablename__ = "clients"
    
    full_name = Column(String(255), nullable=False)
    phone = Column(String(50), nullable=False)
    city = Column(String(100))
    
    cars = relationship("Car", back_populates="client")

class Car(Base, SyncBaseMixin):
    __tablename__ = "cars"
    
    make = Column(String(100), nullable=False)
    model = Column(String(100), nullable=False)
    year = Column(Integer, nullable=False)
    vin = Column(String(100))
    purchase_price = Column(Float, nullable=False)
    currency = Column(String(10), default="TJS")
    status = Column(String(50), default="IN_TRANSIT", index=True)
    purchase_date = Column(Integer, nullable=False)
    
    supplier_uuid = Column(String(36), ForeignKey("suppliers.uuid"), nullable=True)
    client_uuid = Column(String(36), ForeignKey("clients.uuid"), nullable=True)
    
    supplier = relationship("Supplier", back_populates="cars")
    client = relationship("Client", back_populates="cars")
    expenses = relationship("Expense", back_populates="car")

class Expense(Base, SyncBaseMixin):
    __tablename__ = "expenses"
    
    car_uuid = Column(String(36), ForeignKey("cars.uuid"), nullable=False, index=True)
    category = Column(String(100), nullable=False)
    amount = Column(Float, nullable=False)
    currency = Column(String(10), default="TJS")
    description = Column(String(500))
    date = Column(Integer, nullable=False)
    
    car = relationship("Car", back_populates="expenses")

class AuditLog(Base):
    __tablename__ = "audit_logs"
    
    uuid = Column(String(36), primary_key=True)
    entity_type = Column(String(100), nullable=False)
    entity_id = Column(String(36), nullable=False, index=True)
    action_type = Column(String(50), nullable=False)
    old_value = Column(JSON, nullable=True)
    new_value = Column(JSON, nullable=True)
    changed_fields = Column(String(500), nullable=True)
    timestamp = Column(Integer, nullable=False)
    user_id = Column(String(36), nullable=True)

class SyncOperation(Base):
    __tablename__ = "sync_operations"
    
    operation_id = Column(String(36), primary_key=True, index=True)
    entity_type = Column(String(100), nullable=False)
    entity_id = Column(String(36), nullable=False)
    operation_type = Column(String(50), nullable=False)
    payload_json = Column(JSON, nullable=True)
    status = Column(String(50), default="APPLIED")
    created_at = Column(Integer, nullable=False)
    processed_at = Column(Integer, nullable=False)
