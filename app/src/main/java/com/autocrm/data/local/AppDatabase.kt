package com.autocrm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.autocrm.data.local.converters.Converters
import com.autocrm.data.local.dao.*
import com.autocrm.data.local.entities.*

@Database(
    entities  = [
        CarEntity::class, 
        CarPhotoEntity::class, 
        ExpenseEntity::class,
        SupplierEntity::class,
        ClientEntity::class,
        DeliveryEventEntity::class,
        DeliveryPhotoEntity::class,
        SyncQueueEntity::class,
        AuditLogEntity::class
    ],
    version   = 2,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun photoDao(): PhotoDao
    abstract fun supplierDao(): SupplierDao
    abstract fun clientDao(): ClientDao
    abstract fun deliveryDao(): DeliveryDao
    abstract fun syncQueueDao(): SyncQueueDao
    abstract fun auditLogDao(): AuditLogDao
}
