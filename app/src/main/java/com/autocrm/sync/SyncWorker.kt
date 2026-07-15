package com.autocrm.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.room.withTransaction
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.autocrm.data.local.AppDatabase
import com.autocrm.data.local.dao.SyncQueueDao
import com.autocrm.data.local.dao.AuditLogDao
import com.autocrm.data.local.dao.CarDao
import com.autocrm.data.local.entities.AuditLogEntity
import com.autocrm.domain.model.OperationStatus
import com.autocrm.domain.model.EntitySyncStatus
import com.autocrm.data.local.entities.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val db: AppDatabase,
    private val syncQueueDao: SyncQueueDao,
    private val auditLogDao: AuditLogDao,
    private val carDao: CarDao,
    private val conflictResolver: ConflictResolver
) : CoroutineWorker(appContext, workerParams) {

    // Helper for backoff calculation
    private fun getNextRetryDelayMs(retryCount: Int): Long {
        return when (retryCount) {
            0 -> 60_000L        // 1m
            1 -> 300_000L       // 5m
            2 -> 900_000L       // 15m
            3 -> 3600_000L      // 1h
            4 -> 21600_000L     // 6h
            else -> -1L         // DEAD_LETTER
        }
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // ==========================================
            // STEP 1: PUSH (Outbox pattern)
            // ==========================================
            val pendingOps = syncQueueDao.getPendingOperations()
            val now = System.currentTimeMillis()
            
            for (op in pendingOps) {
                // Check if ready for retry based on backoff
                val delay = getNextRetryDelayMs(op.retryCount)
                if (delay == -1L) {
                    syncQueueDao.markDeadLetter(op.id)
                    continue
                }
                
                if (now - op.lastAttemptAt < delay && op.lastAttemptAt != 0L) {
                    continue // Skip, not ready for retry yet
                }

                // Lock operation as IN_PROGRESS
                syncQueueDao.updateStatus(op.id, OperationStatus.IN_PROGRESS)
                
                try {
                    // TODO: Actual API call: api.pushOperation(op.payload, op.operationId)
                    // We assume it returns success boolean or throws
                    val success = true // Placeholder for ACK

                    if (success) {
                        // Mark SENT or Delete
                        syncQueueDao.delete(op.id)
                    } else {
                        syncQueueDao.markFailed(op.id, System.currentTimeMillis())
                    }
                } catch (e: Exception) {
                    syncQueueDao.markFailed(op.id, System.currentTimeMillis())
                }
            }

            // ==========================================
            // STEP 2: PULL (Server changes)
            // ==========================================
            // e.g. val serverCars = api.getUpdatedCars(lastSyncTime)
            val serverCars = emptyList<com.autocrm.domain.model.Car>() // Placeholder

            // ==========================================
            // STEP 3 & 4: MERGE ENGINE & FINALIZE
            // ==========================================
            for (remoteCar in serverCars) {
                db.withTransaction {
                    val localEntity = carDao.getCarWithDetails(remoteCar.uuid)
                    val localCar = localEntity?.car?.toDomain()

                    // Apply Conflict Resolution
                    val resolvedCar = conflictResolver.resolveCarConflict(localCar, remoteCar)
                    
                    // If remote won or merged, save it
                    if (resolvedCar != localCar) {
                        carDao.insertCar(resolvedCar.toEntity().copy(syncStatus = EntitySyncStatus.SYNCED))
                        
                        // Write Audit Log for sync update
                        auditLogDao.insert(AuditLogEntity(
                            uuid = UUID.randomUUID().toString(),
                            entityType = "Car",
                            entityId = resolvedCar.uuid,
                            actionType = "SYNC_MERGE",
                            changedFields = null, oldValue = null, newValue = null
                        ))
                    }
                }
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
