package com.autocrm.data.repository

import androidx.room.withTransaction
import com.autocrm.data.local.AppDatabase
import com.autocrm.data.local.dao.SupplierDao
import com.autocrm.data.local.dao.SyncQueueDao
import com.autocrm.data.local.dao.AuditLogDao
import com.autocrm.data.local.entities.AuditLogEntity
import com.autocrm.data.local.entities.SyncQueueEntity
import com.autocrm.data.local.entities.toDomain
import com.autocrm.data.local.entities.toEntity
import com.autocrm.domain.model.Supplier
import com.autocrm.domain.repository.SupplierRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID

@Singleton
class SupplierRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val supplierDao: SupplierDao,
    private val syncQueueDao: SyncQueueDao,
    private val auditLogDao: AuditLogDao
) : SupplierRepository {

    override fun getAllSuppliers(): Flow<List<Supplier>> {
        return supplierDao.getAllSuppliers().map { list -> list.map { it.toDomain() } }
    }

    override fun getSupplierById(uuid: String): Flow<Supplier?> {
        return supplierDao.getSupplierWithCars(uuid).map { it?.supplier?.toDomain() }
    }

    override suspend fun saveSupplier(supplier: Supplier) {
        db.withTransaction {
            val entity = supplier.toEntity()
            supplierDao.insert(entity)
            
            // Add to SyncQueue (Outbox pattern)
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Supplier",
                entityId = supplier.uuid,
                operationType = "UPDATE",
                payload = null // Or JSON representation
            ))
            
            // Add Audit Log
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Supplier",
                entityId = supplier.uuid,
                actionType = "UPDATE",
                changedFields = null,
                oldValue = null,
                newValue = null
            ))
        }
    }

    override suspend fun deleteSupplier(uuid: String) {
        db.withTransaction {
            supplierDao.softDelete(uuid)
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Supplier",
                entityId = uuid,
                operationType = "DELETE",
                payload = null
            ))
            
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Supplier",
                entityId = uuid,
                actionType = "DELETE",
                changedFields = null,
                oldValue = null,
                newValue = null
            ))
        }
    }
}
