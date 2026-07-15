from pydantic import BaseModel
from typing import Optional, List, Dict, Any
from app.schemas.common import SyncBaseSchema

class CarBase(BaseModel):
    make: str
    model: str
    year: int
    vin: Optional[str] = None
    purchase_price: float
    currency: str = "TJS"
    status: str = "IN_TRANSIT"
    purchase_date: int
    supplier_uuid: Optional[str] = None
    client_uuid: Optional[str] = None

class CarCreate(CarBase):
    pass

class CarUpdate(CarBase):
    pass

class CarInDB(CarBase, SyncBaseSchema):
    pass
