package com.autocrm.data.repository

import androidx.room.withTransaction
import com.autocrm.data.local.AppDatabase
import com.autocrm.data.local.dao.ClientDao
import com.autocrm.data.local.dao.SyncQueueDao
import com.autocrm.data.local.dao.AuditLogDao
import com.autocrm.data.local.entities.AuditLogEntity
import com.autocrm.data.local.entities.SyncQueueEntity
import com.autocrm.data.local.entities.toDomain
import com.autocrm.data.local.entities.toEntity
import com.autocrm.domain.model.Client
import com.autocrm.domain.repository.ClientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import java.util.UUID

@Singleton
class ClientRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val clientDao: ClientDao,
    private val syncQueueDao: SyncQueueDao,
    private val auditLogDao: AuditLogDao
) : ClientRepository {

    override fun getAllClients(): Flow<List<Client>> {
        return clientDao.getAllClients().map { list -> list.map { it.toDomain() } }
    }

    override fun getClientById(uuid: String): Flow<Client?> {
        return clientDao.getClientWithCars(uuid).map { it?.client?.toDomain() }
    }

    override suspend fun saveClient(client: Client) {
        db.withTransaction {
            val entity = client.toEntity()
            clientDao.insert(entity)
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Client",
                entityId = client.uuid,
                operationType = "UPDATE",
                payload = null
            ))
            
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Client",
                entityId = client.uuid,
                actionType = "UPDATE",
                changedFields = null,
                oldValue = null,
                newValue = null
            ))
        }
    }

    override suspend fun deleteClient(uuid: String) {
        db.withTransaction {
            clientDao.softDelete(uuid)
            
            syncQueueDao.insert(SyncQueueEntity(
                operationId = UUID.randomUUID().toString(),
                entityType = "Client",
                entityId = uuid,
                operationType = "DELETE",
                payload = null
            ))
            
            auditLogDao.insert(AuditLogEntity(
                uuid = UUID.randomUUID().toString(),
                entityType = "Client",
                entityId = uuid,
                actionType = "DELETE",
                changedFields = null,
                oldValue = null,
                newValue = null
            ))
        }
    }
}
