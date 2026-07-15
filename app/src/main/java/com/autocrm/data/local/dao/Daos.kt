package com.autocrm.data.local.dao

import androidx.room.*
import com.autocrm.data.local.entities.*
import com.autocrm.domain.model.CarStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query("SELECT * FROM cars WHERE is_deleted = 0 ORDER BY created_at DESC")
    fun getAllCarsWithDetails(): Flow<List<CarWithDetailsEntity>>

    @Transaction
    @Query("SELECT * FROM cars WHERE uuid = :uuid AND is_deleted = 0")
    suspend fun getCarWithDetails(uuid: String): CarWithDetailsEntity?

    @Transaction
    @Query("SELECT * FROM cars WHERE uuid = :uuid AND is_deleted = 0")
    fun getCarWithDetailsFlow(uuid: String): Flow<CarWithDetailsEntity>

    @Transaction
    @Query("""
        SELECT * FROM cars WHERE is_deleted = 0 
        AND (make LIKE '%' || :q || '%' OR model LIKE '%' || :q || '%' OR vin LIKE '%' || :q || '%')
        ORDER BY created_at DESC
    """)
    fun searchCars(q: String): Flow<List<CarWithDetailsEntity>>

    @Transaction
    @Query("SELECT * FROM cars WHERE status = :status AND is_deleted = 0 ORDER BY created_at DESC")
    fun getCarsByStatus(status: CarStatus): Flow<List<CarWithDetailsEntity>>

    @Transaction
    @Query("SELECT * FROM cars WHERE status = 'SOLD' AND is_deleted = 0 ORDER BY sale_date DESC")
    fun getSoldCars(): Flow<List<CarWithDetailsEntity>>

    @Query("SELECT * FROM cars WHERE sync_status != 'SYNCED' AND is_deleted = 0")
    suspend fun getUnsyncedCars(): List<CarEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: CarEntity)

    @Update
    suspend fun updateCar(car: CarEntity)

    @Query("UPDATE cars SET is_deleted = 1, sync_status = 'PENDING', updated_at = :ts WHERE uuid = :uuid")
    suspend fun softDelete(uuid: String, ts: Long = System.currentTimeMillis())

    @Query("UPDATE cars SET server_id = :sid, sync_status = 'SYNCED', updated_at = :ts WHERE uuid = :uuid")
    suspend fun markSynced(uuid: String, sid: String, ts: Long = System.currentTimeMillis())

    @Query("UPDATE cars SET sync_status = 'FAILED' WHERE uuid = :uuid")
    suspend fun markFailed(uuid: String)
}

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE car_uuid = :carUuid ORDER BY date DESC")
    fun getForCar(carUuid: String): Flow<List<ExpenseEntity>>

    @Query("SELECT SUM(amount) FROM expenses WHERE car_uuid = :carUuid")
    fun totalForCar(carUuid: String): Flow<Double?>

    @Query("SELECT * FROM expenses WHERE sync_status != 'SYNCED'")
    suspend fun getUnsynced(): List<ExpenseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(expenses: List<ExpenseEntity>)

    @Update
    suspend fun update(expense: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE uuid = :uuid")
    suspend fun delete(uuid: String)

    @Query("UPDATE expenses SET server_id = :sid, sync_status = 'SYNCED' WHERE uuid = :uuid")
    suspend fun markSynced(uuid: String, sid: String)
}

@Dao
interface PhotoDao {
    @Query("SELECT * FROM car_photos WHERE car_uuid = :carUuid ORDER BY sort_order ASC")
    fun getForCar(carUuid: String): Flow<List<CarPhotoEntity>>

    @Query("SELECT * FROM car_photos WHERE is_primary = 1 AND car_uuid = :carUuid LIMIT 1")
    suspend fun getPrimary(carUuid: String): CarPhotoEntity?

    @Query("SELECT * FROM car_photos WHERE sync_status != 'SYNCED'")
    suspend fun getUnsynced(): List<CarPhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: CarPhotoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<CarPhotoEntity>)

    @Query("DELETE FROM car_photos WHERE uuid = :uuid")
    suspend fun delete(uuid: String)

    @Transaction
    suspend fun setPrimary(carUuid: String, photoUuid: String) {
        clearPrimary(carUuid)
        setAsPrimary(photoUuid)
    }

    @Query("UPDATE car_photos SET is_primary = 0 WHERE car_uuid = :carUuid")
    suspend fun clearPrimary(carUuid: String)

    @Query("UPDATE car_photos SET is_primary = 1 WHERE uuid = :uuid")
    suspend fun setAsPrimary(uuid: String)

    @Query("UPDATE car_photos SET remote_url = :url, sync_status = 'SYNCED' WHERE uuid = :uuid")
    suspend fun markUploaded(uuid: String, url: String)
}
