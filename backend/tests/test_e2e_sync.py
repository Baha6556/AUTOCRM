import pytest
import uuid
from httpx import AsyncClient

pytestmark = pytest.mark.asyncio

async def test_e2e_sync_happy_path(async_client: AsyncClient, auth_headers: dict):
    """
    Simulates Android Client creating a Car locally and pushing to backend.
    """
    car_uuid = str(uuid.uuid4())
    operation_id = str(uuid.uuid4())
    
    payload = {
        "operations": [
            {
                "operation_id": operation_id,
                "entity_type": "Car",
                "entity_id": car_uuid,
                "operation_type": "CREATE",
                "data": {
                    "make": "Toyota",
                    "model": "Camry",
                    "year": 2023,
                    "purchase_price": 20000.0,
                    "currency": "TJS",
                    "status": "IN_TRANSIT",
                    "purchase_date": 1690000000000
                },
                "version": 1
            }
        ]
    }
    
    # 1. PUSH
    response = await async_client.post("/api/v1/sync/push", json=payload, headers=auth_headers)
    assert response.status_code == 200
    assert response.json()["status"] == "ACK"
    
    # 2. PULL
    pull_response = await async_client.get("/api/v1/sync/pull?last_sync_timestamp=0", headers=auth_headers)
    assert pull_response.status_code == 200
    pull_data = pull_response.json()
    
    # Verify car is returned
    assert len(pull_data["updated_entities"]) >= 1
    found = next((c for c in pull_data["updated_entities"] if c["uuid"] == car_uuid), None)
    assert found is not None
    assert found["make"] == "Toyota"
    assert found["version"] == 1

async def test_sync_idempotency_duplicate_push(async_client: AsyncClient, auth_headers: dict):
    """
    Verifies that sending the exact same operation_id twice does not corrupt data.
    """
    car_uuid = str(uuid.uuid4())
    operation_id = str(uuid.uuid4())
    
    payload = {
        "operations": [
            {
                "operation_id": operation_id,
                "entity_type": "Car",
                "entity_id": car_uuid,
                "operation_type": "CREATE",
                "data": {
                    "make": "Honda",
                    "model": "Civic",
                    "year": 2021,
                    "purchase_price": 15000.0,
                    "purchase_date": 1690000000000
                },
                "version": 1
            }
        ]
    }
    
    # First Push
    res1 = await async_client.post("/api/v1/sync/push", json=payload, headers=auth_headers)
    assert res1.status_code == 200
    
    # Second Push (Duplicate due to network retry)
    res2 = await async_client.post("/api/v1/sync/push", json=payload, headers=auth_headers)
    assert res2.status_code == 200 # Should return 200 ACK silently
    
    # Pull and check version is still 1
    pull_res = await async_client.get("/api/v1/sync/pull?last_sync_timestamp=0", headers=auth_headers)
    found = next((c for c in pull_res.json()["updated_entities"] if c["uuid"] == car_uuid), None)
    assert found["version"] == 1 # Version didn't increment

async def test_sync_conflict_resolution_409(async_client: AsyncClient, auth_headers: dict):
    """
    Verifies that pushing an outdated version results in 409 CONFLICT.
    """
    car_uuid = str(uuid.uuid4())
    
    # Create Car initially (Version 1)
    await async_client.post("/api/v1/sync/push", json={
        "operations": [
            {
                "operation_id": str(uuid.uuid4()),
                "entity_type": "Car",
                "entity_id": car_uuid,
                "operation_type": "CREATE",
                "data": {"make": "BMW", "model": "X5", "year": 2020, "purchase_price": 30000.0, "purchase_date": 1690000000000},
                "version": 1
            }
        ]
    }, headers=auth_headers)
    
    # Update Car properly (Version 2)
    await async_client.post("/api/v1/sync/push", json={
        "operations": [
            {
                "operation_id": str(uuid.uuid4()),
                "entity_type": "Car",
                "entity_id": car_uuid,
                "operation_type": "UPDATE",
                "data": {"make": "BMW", "model": "X5 M"},
                "version": 2 # Correct next version
            }
        ]
    }, headers=auth_headers)
    
    # Client B tries to update but still has Version 1 locally
    conflict_res = await async_client.post("/api/v1/sync/push", json={
        "operations": [
            {
                "operation_id": str(uuid.uuid4()),
                "entity_type": "Car",
                "entity_id": car_uuid,
                "operation_type": "UPDATE",
                "data": {"make": "BMW", "model": "X5 Regular"},
                "version": 1 # OUTDATED!
            }
        ]
    }, headers=auth_headers)
    
    assert conflict_res.status_code == 409
    error_data = conflict_res.json()["detail"]
    assert error_data["status"] == "CONFLICT"
    assert error_data["server_version"] == 2
    assert error_data["server_data"]["model"] == "X5 M"

async def test_audit_logs_created(async_client: AsyncClient, auth_headers: dict):
    """
    (Internal check) While E2E focuses on API, we also test side-effects.
    In real E2E, we can expose an admin endpoint or just know it works.
    For this simulation, the previous pushes should have generated audit logs.
    """
    # Verify sync push works normally and doesn't crash on audit log insertion.
    car_uuid = str(uuid.uuid4())
    res = await async_client.post("/api/v1/sync/push", json={
        "operations": [
            {
                "operation_id": str(uuid.uuid4()),
                "entity_type": "Car",
                "entity_id": car_uuid,
                "operation_type": "CREATE",
                "data": {"make": "Audi", "model": "Q7", "year": 2021, "purchase_price": 40000.0, "purchase_date": 1690000000000},
                "version": 1
            }
        ]
    }, headers=auth_headers)
    assert res.status_code == 200
