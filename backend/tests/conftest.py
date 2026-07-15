import pytest
import pytest_asyncio
from httpx import AsyncClient
from typing import AsyncGenerator
from sqlalchemy.ext.asyncio import create_async_engine, AsyncSession
from sqlalchemy.orm import sessionmaker

from app.main import app
from app.db.base import Base
from app.db.session import get_db
from app.core.config import settings
from app.db.models import User
from app.core.security import get_password_hash

# Use a test database URI (e.g. overriding via env var in CI, or just a separate DB name)
TEST_DATABASE_URL = "postgresql+asyncpg://postgres:postgres@localhost:5432/autocrm_test"

engine_test = create_async_engine(TEST_DATABASE_URL, echo=False)
TestingSessionLocal = sessionmaker(engine_test, class_=AsyncSession, expire_on_commit=False)

async def override_get_db() -> AsyncGenerator[AsyncSession, None]:
    async with TestingSessionLocal() as session:
        yield session

app.dependency_overrides[get_db] = override_get_db

@pytest_asyncio.fixture(scope="session", autouse=True)
async def setup_database():
    """Create test database tables and a default test user."""
    async with engine_test.begin() as conn:
        await conn.run_sync(Base.metadata.drop_all)
        await conn.run_sync(Base.metadata.create_all)
        
    async with TestingSessionLocal() as session:
        # Create a test user for auth
        test_user = User(
            uuid="test_user_uuid",
            email="test@autocrm.com",
            hashed_password=get_password_hash("testpassword"),
            full_name="E2E Test User"
        )
        session.add(test_user)
        await session.commit()
        
    yield
    
    async with engine_test.begin() as conn:
        await conn.run_sync(Base.metadata.drop_all)

@pytest_asyncio.fixture
async def async_client() -> AsyncGenerator[AsyncClient, None]:
    async with AsyncClient(app=app, base_url="http://test") as ac:
        yield ac

@pytest_asyncio.fixture
async def auth_headers(async_client: AsyncClient) -> dict:
    """Login and get auth headers"""
    response = await async_client.post(
        "/api/v1/auth/login", 
        json={"email": "test@autocrm.com", "password": "testpassword"}
    )
    token = response.json()["access_token"]
    return {"Authorization": f"Bearer {token}"}
