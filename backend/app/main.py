from fastapi import FastAPI
from app.api.routes import auth, sync
from app.core.config import settings

from app.core.logger import LoggingMiddleware

app = FastAPI(
    title=settings.PROJECT_NAME,
    version=settings.VERSION,
    description="Backend API for AutoCRM (Offline-first Sync & ERP)",
)

app.add_middleware(LoggingMiddleware)

# Include routers
app.include_router(auth.router, prefix=f"{settings.API_V1_STR}/auth", tags=["auth"])
app.include_router(sync.router, prefix=f"{settings.API_V1_STR}/sync", tags=["sync"])

@app.get("/")
async def root():
    return {"message": "Welcome to AutoCRM Backend Sync API"}
