package com.autocrm.data.local.dao

import androidx.room.*
import com.autocrm.data.local.entities.ClientEntity
import com.autocrm.data.local.entities.ClientWithCarsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients WHERE is_deleted = 0 ORDER BY full_name ASC")
    fun getAllClients(): Flow<List<ClientEntity>>

    @Transaction
    @Query("SELECT * FROM clients WHERE uuid = :uuid AND is_deleted = 0")
    fun getClientWithCars(uuid: String): Flow<ClientWithCarsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: ClientEntity)

    @Update
    suspend fun update(client: ClientEntity)

    @Query("UPDATE clients SET is_deleted = 1, sync_status = 'PENDING', updated_at = :ts WHERE uuid = :uuid")
    suspend fun softDelete(uuid: String, ts: Long = System.currentTimeMillis())
    
    @Query("SELECT * FROM clients WHERE sync_status != 'SYNCED' AND is_deleted = 0")
    suspend fun getUnsyncedClients(): List<ClientEntity>
    
    @Query("UPDATE clients SET server_id = :sid, sync_status = 'SYNCED', updated_at = :ts WHERE uuid = :uuid")
    suspend fun markSynced(uuid: String, sid: String, ts: Long = System.currentTimeMillis())
    
    @Query("UPDATE clients SET sync_status = 'FAILED' WHERE uuid = :uuid")
    suspend fun markFailed(uuid: String)
}
