package com.autocrm.data.local.dao

import androidx.room.*
import com.autocrm.data.local.entities.DeliveryEventEntity
import com.autocrm.data.local.entities.DeliveryEventWithPhotosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryDao {
    @Transaction
    @Query("SELECT * FROM delivery_events WHERE car_uuid = :carUuid AND is_deleted = 0 ORDER BY date ASC")
    fun getEventsForCar(carUuid: String): Flow<List<DeliveryEventWithPhotosEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: DeliveryEventEntity)

    @Update
    suspend fun update(event: DeliveryEventEntity)

    @Query("UPDATE delivery_events SET is_deleted = 1, sync_status = 'PENDING', updated_at = :ts WHERE uuid = :uuid")
    suspend fun softDelete(uuid: String, ts: Long = System.currentTimeMillis())
    
    @Query("SELECT * FROM delivery_events WHERE sync_status != 'SYNCED' AND is_deleted = 0")
    suspend fun getUnsyncedEvents(): List<DeliveryEventEntity>
    
    @Query("UPDATE delivery_events SET server_id = :sid, sync_status = 'SYNCED', updated_at = :ts WHERE uuid = :uuid")
    suspend fun markSynced(uuid: String, sid: String, ts: Long = System.currentTimeMillis())
    
    @Query("UPDATE delivery_events SET sync_status = 'FAILED' WHERE uuid = :uuid")
    suspend fun markFailed(uuid: String)
}
