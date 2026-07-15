package com.autocrm.data.local.dao

import androidx.room.*
import com.autocrm.data.local.entities.SyncQueueEntity
import com.autocrm.domain.model.OperationStatus

@Dao
interface SyncQueueDao {
    @Query("SELECT * FROM sync_queue WHERE status IN ('PENDING', 'FAILED') ORDER BY created_at ASC")
    suspend fun getPendingOperations(): List<SyncQueueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(operation: SyncQueueEntity)

    @Query("UPDATE sync_queue SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: OperationStatus)

    @Query("UPDATE sync_queue SET retry_count = retry_count + 1, status = 'FAILED', last_attempt_at = :timestamp WHERE id = :id")
    suspend fun markFailed(id: Long, timestamp: Long = System.currentTimeMillis())
    
    @Query("UPDATE sync_queue SET status = 'DEAD_LETTER' WHERE id = :id")
    suspend fun markDeadLetter(id: Long)

    @Query("DELETE FROM sync_queue WHERE id = :id")
    suspend fun delete(id: Long)
}
