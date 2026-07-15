package com.autocrm.data.repository

import androidx.room.withTransaction
import com.autocrm.data.local.AppDatabase
import com.autocrm.data.local.dao.CarDao
import com.autocrm.data.local.dao.ExpenseDao
import com.autocrm.data.local.dao.PhotoDao
import com.autocrm.data.local.dao.SyncQueueDao
import com.autocrm.data.local.dao.AuditLogDao
import com.autocrm.data.local.entities.*
import com.autocrm.domain.model.*
import com.autocrm.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val carDao: CarDao,
    private val expenseDao: ExpenseDao,
    private val photoDao: PhotoDao,
    private val syncQueueDao: SyncQueueDao,
    private val auditLogDao: AuditLogDao
) : CarRepository {

    override fun getAllCars(): Flow<List<CarWithDetails>> =
        carDao.getAllCarsWithDetails().map { list -> list.map { it.toDomain() } }

    override fun getSoldCars(): Flow<List<CarWithDetails>> =
        carDao.getSoldCars().map { list -> list.map { it.toDomain() } }

    override fun getCarsByStatus(status: CarStatus): Flow<List<CarWithDetails>> =
        carDao.getCarsByStatus(status).map { list -> list.map { it.toDomain() } }

    override fun searchCars(query: String): Flow<List<CarWithDetails>> =
        carDao.searchCars(query).map { list -> list.map { it.toDomain() } }

    override suspend fun getCarById(uuid: String): CarWithDetails? =
        carDao.getCarWithDetails(uuid)?.toDomain()

    override fun getCarByIdFlow(uuid: String): Flow<CarWithDetails> =
        carDao.getCarWithDetailsFlow(uuid).map { it.toDomain() }

    override suspend fun saveCar(car: Car) {
        db.withTransaction {
            carDao.insertCar(car.toEntity())
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Car",
                entityId = car.uuid,
                operationType = "UPDATE",
                payload = null
            ))
            
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Car",
                entityId = car.uuid,
                actionType = "UPDATE",
                changedFields = null, oldValue = null, newValue = null
            ))
        }
    }

    override suspend fun deleteCar(uuid: String) {
        db.withTransaction {
            carDao.softDelete(uuid)
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Car",
                entityId = uuid,
                operationType = "DELETE",
                payload = null
            ))
            
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Car",
                entityId = uuid,
                actionType = "DELETE",
                changedFields = null, oldValue = null, newValue = null
            ))
        }
    }

    // Analytics calculations were moved to Domain layer (AnalyticsUseCases)
    // The DAO logic remains to provide raw aggregates if needed, but DashboardStats logic is now in UseCase.
    override fun getDashboardStats(): Flow<DashboardStats> {
        throw UnsupportedOperationException("Moved to AnalyticsLayer")
    }

    override fun getMonthlyProfits(): Flow<List<MonthlyProfit>> {
        throw UnsupportedOperationException("Moved to AnalyticsLayer")
    }

    override suspend fun addExpense(expense: Expense) {
        db.withTransaction {
            expenseDao.insert(expense.toEntity())
            
            // Outbox for Expense
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Expense",
                entityId = expense.uuid,
                operationType = "CREATE",
                payload = null
            ))
            
            // Audit Log
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Expense",
                entityId = expense.uuid,
                actionType = "CREATE",
                changedFields = null, oldValue = null, newValue = null
            ))
            
            // Touch car updatedAt
            carDao.getCarWithDetails(expense.carUuid)?.car?.let { entity ->
                carDao.updateCar(entity.copy(updatedAt = System.currentTimeMillis(), syncStatus = EntitySyncStatus.PENDING))
            }
        }
    }

    override suspend fun updateExpense(expense: Expense) {
        db.withTransaction {
            expenseDao.update(expense.toEntity())
            // Note: In append-only model we might not allow update, but we keep this for local edit before sync
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Expense",
                entityId = expense.uuid,
                operationType = "UPDATE",
                payload = null
            ))
            
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Expense",
                entityId = expense.uuid,
                actionType = "UPDATE",
                changedFields = null, oldValue = null, newValue = null
            ))
        }
    }

    override suspend fun deleteExpense(uuid: String) {
        db.withTransaction {
            // Note: Soft delete for Expense to match append-only requirements. We'll physical delete for now if it's local only.
            expenseDao.delete(uuid)
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Expense",
                entityId = uuid,
                operationType = "DELETE",
                payload = null
            ))
            
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Expense",
                entityId = uuid,
                actionType = "DELETE",
                changedFields = null, oldValue = null, newValue = null
            ))
        }
    }

    override suspend fun addPhoto(photo: CarPhoto) {
        db.withTransaction {
            photoDao.insert(photo.toEntity())
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "CarPhoto",
                entityId = photo.uuid,
                operationType = "CREATE",
                payload = null
            ))
        }
    }

    override suspend fun deletePhoto(uuid: String) {
        db.withTransaction {
            photoDao.delete(uuid) // Should be soft delete in production
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "CarPhoto",
                entityId = uuid,
                operationType = "DELETE",
                payload = null
            ))
        }
    }

    override suspend fun setPrimaryPhoto(carUuid: String, photoUuid: String) {
        photoDao.setPrimary(carUuid, photoUuid)
    }
}
