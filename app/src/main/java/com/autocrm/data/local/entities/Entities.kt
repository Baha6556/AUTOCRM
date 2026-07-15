package com.autocrm.data.local.entities

import androidx.room.*
import com.autocrm.domain.model.CarStatus
import com.autocrm.domain.model.DeliveryStatus
import com.autocrm.domain.model.ExpenseCategory
import com.autocrm.domain.model.EntitySyncStatus
import com.autocrm.domain.model.OperationStatus

// ==========================================
// 1. SUPPLIER ENTITY
// ==========================================
@Entity(tableName = "suppliers")
data class SupplierEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")         val uuid: String,
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "contact_person") val contactPerson: String?,
    @ColumnInfo(name = "phone")        val phone: String?,
    @ColumnInfo(name = "telegram")     val telegram: String?,
    @ColumnInfo(name = "whatsapp")     val whatsapp: String?,
    @ColumnInfo(name = "country")      val country: String,
    @ColumnInfo(name = "city")         val city: String?,
    @ColumnInfo(name = "address")      val address: String?,
    @ColumnInfo(name = "notes")        val notes: String?,
    @ColumnInfo(name = "rating")       val rating: Float = 0f,
    // BaseSyncEntity fields
    @ColumnInfo(name = "server_id")    val serverId: String? = null,
    @ColumnInfo(name = "created_at")   val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")   val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted")   val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status")  val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    @ColumnInfo(name = "version")      val version: Long = 1
)

// ==========================================
// 2. CLIENT ENTITY
// ==========================================
@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")         val uuid: String,
    @ColumnInfo(name = "full_name")    val fullName: String,
    @ColumnInfo(name = "phone")        val phone: String,
    @ColumnInfo(name = "telegram")     val telegram: String?,
    @ColumnInfo(name = "whatsapp")     val whatsapp: String?,
    @ColumnInfo(name = "city")         val city: String?,
    @ColumnInfo(name = "preferred_brands") val preferredBrands: String?,
    @ColumnInfo(name = "notes")        val notes: String?,
    // BaseSyncEntity fields
    @ColumnInfo(name = "server_id")    val serverId: String? = null,
    @ColumnInfo(name = "created_at")   val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")   val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted")   val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status")  val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    @ColumnInfo(name = "version")      val version: Long = 1
)

// ==========================================
// 3. CAR ENTITY
// ==========================================
@Entity(
    tableName = "cars",
    foreignKeys = [
        ForeignKey(entity = SupplierEntity::class, parentColumns = ["uuid"], childColumns = ["supplier_uuid"], onDelete = ForeignKey.SET_NULL),
        ForeignKey(entity = ClientEntity::class, parentColumns = ["uuid"], childColumns = ["client_uuid"], onDelete = ForeignKey.SET_NULL)
    ],
    indices = [
        Index("supplier_uuid"), 
        Index("client_uuid"),
        Index("status"),
        Index("sync_status"),
        Index("updated_at")
    ]
)
data class CarEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")         val uuid: String,
    @ColumnInfo(name = "make")         val make: String,
    @ColumnInfo(name = "model")        val model: String,
    @ColumnInfo(name = "year")         val year: Int,
    @ColumnInfo(name = "vin")          val vin: String?           = null,
    @ColumnInfo(name = "mileage")      val mileage: Int?          = null,
    @ColumnInfo(name = "country")      val countryOfOrigin: String = "CN",
    @ColumnInfo(name = "color")        val color: String?         = null,
    @ColumnInfo(name = "engine_vol")   val engineVolume: Double?  = null,
    @ColumnInfo(name = "transmission") val transmission: String?  = null,
    @ColumnInfo(name = "purchase_price") val purchasePrice: Double,
    @ColumnInfo(name = "est_sale_price") val estimatedSalePrice: Double? = null,
    @ColumnInfo(name = "sale_price")   val salePrice: Double?     = null,
    @ColumnInfo(name = "currency")     val currency: String       = "TJS",
    @ColumnInfo(name = "status")       val status: CarStatus      = CarStatus.IN_TRANSIT,
    @ColumnInfo(name = "purchase_date") val purchaseDate: Long,
    @ColumnInfo(name = "sale_date")    val saleDate: Long?        = null,
    @ColumnInfo(name = "arrival_date") val arrivalDate: Long?     = null,
    @ColumnInfo(name = "notes")        val notes: String?         = null,
    
    // Foreign Keys
    @ColumnInfo(name = "supplier_uuid") val supplierUuid: String? = null,
    @ColumnInfo(name = "client_uuid")   val clientUuid: String?   = null,
    @ColumnInfo(name = "delivery_status") val deliveryStatus: DeliveryStatus = DeliveryStatus.BOUGHT,

    // BaseSyncEntity fields
    @ColumnInfo(name = "server_id")    val serverId: String? = null,
    @ColumnInfo(name = "created_at")   val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")   val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted")   val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status")  val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    @ColumnInfo(name = "version")      val version: Long = 1
)

// ==========================================
// 4. EXPENSE ENTITY
// ==========================================
@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(entity = CarEntity::class, parentColumns = ["uuid"], childColumns = ["car_uuid"], onDelete = ForeignKey.CASCADE)],
    indices = [
        Index("car_uuid"),
        Index("sync_status"),
        Index("updated_at")
    ]
)
data class ExpenseEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")        val uuid: String,
    @ColumnInfo(name = "car_uuid")    val carUuid: String,
    @ColumnInfo(name = "category")    val category: ExpenseCategory,
    @ColumnInfo(name = "amount")      val amount: Double,
    @ColumnInfo(name = "currency")    val currency: String = "TJS",
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "date")        val date: Long = System.currentTimeMillis(),
    // BaseSyncEntity fields
    @ColumnInfo(name = "server_id")   val serverId: String? = null,
    @ColumnInfo(name = "created_at")  val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")  val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted")  val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    @ColumnInfo(name = "version")     val version: Long = 1
)

// ==========================================
// 5. CAR PHOTO ENTITY
// ==========================================
@Entity(
    tableName = "car_photos",
    foreignKeys = [ForeignKey(entity = CarEntity::class, parentColumns = ["uuid"], childColumns = ["car_uuid"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("car_uuid")]
)
data class CarPhotoEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")        val uuid: String,
    @ColumnInfo(name = "car_uuid")    val carUuid: String,
    @ColumnInfo(name = "file_path")   val filePath: String,
    @ColumnInfo(name = "remote_url")  val remoteUrl: String? = null,
    @ColumnInfo(name = "sort_order")  val sortOrder: Int = 0,
    @ColumnInfo(name = "is_primary")  val isPrimary: Boolean = false,
    // BaseSyncEntity fields
    @ColumnInfo(name = "server_id")   val serverId: String? = null,
    @ColumnInfo(name = "created_at")  val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")  val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted")  val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    @ColumnInfo(name = "version")     val version: Long = 1
)

// ==========================================
// 6. DELIVERY EVENT ENTITY
// ==========================================
@Entity(
    tableName = "delivery_events",
    foreignKeys = [ForeignKey(entity = CarEntity::class, parentColumns = ["uuid"], childColumns = ["car_uuid"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("car_uuid")]
)
data class DeliveryEventEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")        val uuid: String,
    @ColumnInfo(name = "car_uuid")    val carUuid: String,
    @ColumnInfo(name = "status")      val status: DeliveryStatus,
    @ColumnInfo(name = "date")        val date: Long,
    @ColumnInfo(name = "location")    val location: String?,
    @ColumnInfo(name = "port")        val port: String?,
    @ColumnInfo(name = "carrier_name") val carrierName: String?,
    @ColumnInfo(name = "container_number") val containerNumber: String?,
    @ColumnInfo(name = "notes")       val notes: String?,
    @ColumnInfo(name = "est_delivery_date") val estimatedDeliveryDate: Long?,
    @ColumnInfo(name = "act_delivery_date") val actualDeliveryDate: Long?,
    // BaseSyncEntity fields
    @ColumnInfo(name = "server_id")   val serverId: String? = null,
    @ColumnInfo(name = "created_at")  val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")  val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted")  val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    @ColumnInfo(name = "version")     val version: Long = 1
)

// ==========================================
// 7. DELIVERY PHOTO ENTITY
// ==========================================
@Entity(
    tableName = "delivery_photos",
    foreignKeys = [ForeignKey(entity = DeliveryEventEntity::class, parentColumns = ["uuid"], childColumns = ["event_uuid"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("event_uuid")]
)
data class DeliveryPhotoEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")        val uuid: String,
    @ColumnInfo(name = "event_uuid")  val eventUuid: String,
    @ColumnInfo(name = "file_path")   val filePath: String,
    @ColumnInfo(name = "remote_url")  val remoteUrl: String? = null,
    // BaseSyncEntity fields
    @ColumnInfo(name = "server_id")   val serverId: String? = null,
    @ColumnInfo(name = "created_at")  val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")  val updatedAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "is_deleted")  val isDeleted: Boolean = false,
    @ColumnInfo(name = "sync_status") val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    @ColumnInfo(name = "version")     val version: Long = 1
)

// ==========================================
// 8. SYNC QUEUE (OUTBOX) ENTITY
// ==========================================
@Entity(
    tableName = "sync_queue",
    indices = [
        Index("status"),
        Index("entity_type"),
        Index("entity_id")
    ]
)
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")          val id: Long = 0,
    @ColumnInfo(name = "operation_id") val operationId: String,
    @ColumnInfo(name = "entity_type") val entityType: String,
    @ColumnInfo(name = "entity_id")   val entityId: String,
    @ColumnInfo(name = "operation_type") val operationType: String, // CREATE, UPDATE, DELETE
    @ColumnInfo(name = "payload")     val payload: String?,
    @ColumnInfo(name = "created_at")  val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "last_attempt_at") val lastAttemptAt: Long = 0,
    @ColumnInfo(name = "retry_count") val retryCount: Int = 0,
    @ColumnInfo(name = "status")      val status: OperationStatus = OperationStatus.PENDING
)

// ==========================================
// 9. AUDIT LOG ENTITY
// ==========================================
@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid")         val uuid: String,
    @ColumnInfo(name = "entity_type")  val entityType: String,
    @ColumnInfo(name = "entity_id")    val entityId: String,
    @ColumnInfo(name = "action_type")  val actionType: String,
    @ColumnInfo(name = "changed_fields") val changedFields: String?,
    @ColumnInfo(name = "old_value")    val oldValue: String?,
    @ColumnInfo(name = "new_value")    val newValue: String?,
    @ColumnInfo(name = "timestamp")    val timestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "user_id")      val userId: String? = null
)

// ==========================================
// RELATIONSHIPS
// ==========================================

data class DeliveryEventWithPhotosEntity(
    @Embedded val event: DeliveryEventEntity,
    @Relation(parentColumn = "uuid", entityColumn = "event_uuid")
    val photos: List<DeliveryPhotoEntity>
)

data class CarWithDetailsEntity(
    @Embedded val car: CarEntity,
    
    @Relation(parentColumn = "uuid", entityColumn = "car_uuid")
    val expenses: List<ExpenseEntity>,
    
    @Relation(parentColumn = "uuid", entityColumn = "car_uuid")
    val photos: List<CarPhotoEntity>,
    
    @Relation(parentColumn = "uuid", entityColumn = "car_uuid", entity = DeliveryEventEntity::class)
    val deliveryEvents: List<DeliveryEventWithPhotosEntity>,
    
    @Relation(parentColumn = "supplier_uuid", entityColumn = "uuid")
    val supplier: SupplierEntity?,
    
    @Relation(parentColumn = "client_uuid", entityColumn = "uuid")
    val client: ClientEntity?
)

data class SupplierWithCarsEntity(
    @Embedded val supplier: SupplierEntity,
    @Relation(parentColumn = "uuid", entityColumn = "supplier_uuid", entity = CarEntity::class)
    val cars: List<CarWithDetailsEntity>
)

data class ClientWithCarsEntity(
    @Embedded val client: ClientEntity,
    @Relation(parentColumn = "uuid", entityColumn = "client_uuid", entity = CarEntity::class)
    val cars: List<CarWithDetailsEntity>
)
