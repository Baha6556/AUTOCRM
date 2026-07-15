package com.autocrm.data.local.dao

import androidx.room.*
import com.autocrm.data.local.entities.SupplierEntity
import com.autocrm.data.local.entities.SupplierWithCarsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {
    @Query("SELECT * FROM suppliers WHERE is_deleted = 0 ORDER BY created_at DESC")
    fun getAllSuppliers(): Flow<List<SupplierEntity>>

    @Transaction
    @Query("SELECT * FROM suppliers WHERE uuid = :uuid AND is_deleted = 0")
    fun getSupplierWithCars(uuid: String): Flow<SupplierWithCarsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(supplier: SupplierEntity)

    @Update
    suspend fun update(supplier: SupplierEntity)

    @Query("UPDATE suppliers SET is_deleted = 1, sync_status = 'PENDING', updated_at = :ts WHERE uuid = :uuid")
    suspend fun softDelete(uuid: String, ts: Long = System.currentTimeMillis())
    
    @Query("SELECT * FROM suppliers WHERE sync_status != 'SYNCED' AND is_deleted = 0")
    suspend fun getUnsyncedSuppliers(): List<SupplierEntity>
    
    @Query("UPDATE suppliers SET server_id = :sid, sync_status = 'SYNCED', updated_at = :ts WHERE uuid = :uuid")
    suspend fun markSynced(uuid: String, sid: String, ts: Long = System.currentTimeMillis())
    
    @Query("UPDATE suppliers SET sync_status = 'FAILED' WHERE uuid = :uuid")
    suspend fun markFailed(uuid: String)
}
