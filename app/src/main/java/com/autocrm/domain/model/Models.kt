package com.autocrm.domain.model

import java.util.UUID

enum class CarStatus(val displayName: String, val colorHex: Long) {
    IN_TRANSIT("В пути",        0xFF4A9EFF),
    FOR_SALE  ("На продаже",    0xFF00E5A0),
    SOLD      ("Продана",       0xFF9090A8),
    REPAIR    ("Ремонт",        0xFFFFB347),
    RESERVED  ("Забронирована", 0xFFAA80FF)
}

enum class EntitySyncStatus { PENDING, SYNCED, FAILED, DIRTY }

enum class OperationStatus { PENDING, IN_PROGRESS, SENT, FAILED, DEAD_LETTER }

enum class ExpenseCategory(val displayName: String, val icon: String) {
    PURCHASE   ("Покупка",      "💰"),
    DELIVERY   ("Доставка",     "🚢"),
    CUSTOMS    ("Растаможка",   "🏛️"),
    REPAIR     ("Ремонт",       "🔧"),
    INSPECTION ("Диагностика",  "🔍"),
    INSURANCE  ("Страховка",    "🛡️"),
    PARKING    ("Хранение",     "🅿️"),
    OTHER      ("Прочее",       "📋")
}

enum class DeliveryStatus(val displayName: String) {
    BOUGHT("Куплена"),
    AT_PORT("В порту"),
    LOADED("Загружена"),
    IN_TRANSIT("В пути"),
    CUSTOMS("На таможне"),
    DELIVERED("Доставлена"),
    ON_SALE("На продаже")
}

data class Supplier(
    val uuid: String = UUID.randomUUID().toString(),
    val companyName: String,
    val contactPerson: String? = null,
    val phone: String? = null,
    val telegram: String? = null,
    val whatsapp: String? = null,
    val country: String,
    val city: String? = null,
    val address: String? = null,
    val notes: String? = null,
    val rating: Float = 0f,
    val serverId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isDeleted: Boolean = false,
    val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    val version: Long = 1
)

data class Client(
    val uuid: String = UUID.randomUUID().toString(),
    val fullName: String,
    val phone: String,
    val telegram: String? = null,
    val whatsapp: String? = null,
    val city: String? = null,
    val preferredBrands: String? = null,
    val notes: String? = null,
    val serverId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isDeleted: Boolean = false,
    val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    val version: Long = 1
)

data class DeliveryEvent(
    val uuid: String = UUID.randomUUID().toString(),
    val carUuid: String,
    val status: DeliveryStatus,
    val date: Long,
    val location: String? = null,
    val port: String? = null,
    val carrierName: String? = null,
    val containerNumber: String? = null,
    val notes: String? = null,
    val estimatedDeliveryDate: Long? = null,
    val actualDeliveryDate: Long? = null,
    val serverId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isDeleted: Boolean = false,
    val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    val version: Long = 1
)

data class DeliveryPhoto(
    val uuid: String = UUID.randomUUID().toString(),
    val eventUuid: String,
    val filePath: String,
    val remoteUrl: String? = null,
    val serverId: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isDeleted: Boolean = false,
    val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    val version: Long = 1
)

data class Car(
    val uuid: String            = UUID.randomUUID().toString(),
    val serverId: String?       = null,
    val make: String,
    val model: String,
    val year: Int,
    val vin: String?            = null,
    val mileage: Int?           = null,
    val countryOfOrigin: String = "CN",
    val color: String?          = null,
    val engineVolume: Double?   = null,
    val transmission: String?   = null,
    val purchasePrice: Double,
    val estimatedSalePrice: Double? = null,
    val salePrice: Double?      = null,
    val currency: String        = "TJS",
    val status: CarStatus       = CarStatus.IN_TRANSIT,
    val purchaseDate: Long      = System.currentTimeMillis(),
    val saleDate: Long?         = null,
    val arrivalDate: Long?      = null,
    val notes: String?          = null,
    
    val supplierUuid: String?   = null,
    val clientUuid: String?     = null,
    val deliveryStatus: DeliveryStatus = DeliveryStatus.BOUGHT,

    val createdAt: Long         = System.currentTimeMillis(),
    val updatedAt: Long         = System.currentTimeMillis(),
    val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    val isDeleted: Boolean      = false,
    val version: Long           = 1
)

data class CarPhoto(
    val uuid: String           = UUID.randomUUID().toString(),
    val serverId: String?      = null,
    val carUuid: String,
    val filePath: String,
    val remoteUrl: String?     = null,
    val sortOrder: Int         = 0,
    val isPrimary: Boolean     = false,
    val createdAt: Long        = System.currentTimeMillis(),
    val updatedAt: Long        = System.currentTimeMillis(),
    val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    val isDeleted: Boolean     = false,
    val version: Long          = 1
)

data class Expense(
    val uuid: String              = UUID.randomUUID().toString(),
    val serverId: String?         = null,
    val carUuid: String,
    val category: ExpenseCategory,
    val amount: Double,
    val currency: String          = "TJS",
    val description: String?      = null,
    val date: Long                = System.currentTimeMillis(),
    val createdAt: Long           = System.currentTimeMillis(),
    val updatedAt: Long           = System.currentTimeMillis(),
    val syncStatus: EntitySyncStatus = EntitySyncStatus.PENDING,
    val isDeleted: Boolean        = false,
    val version: Long             = 1
)

data class CarWithDetails(
    val car: Car,
    val expenses: List<Expense>  = emptyList(),
    val photos: List<CarPhoto>   = emptyList(),
    val deliveryEvents: List<DeliveryEvent> = emptyList(),
    val supplier: Supplier? = null,
    val client: Client? = null
) {
    val totalExpenses: Double
        get() = expenses.sumOf { it.amount }

    val netProfit: Double?
        get() = car.salePrice?.let { it - car.purchasePrice - totalExpenses }

    val estimatedProfit: Double?
        get() = car.estimatedSalePrice?.let { it - car.purchasePrice - totalExpenses }

    val daysOnSale: Long
        get() {
            val end = car.saleDate ?: System.currentTimeMillis()
            return (end - car.purchaseDate) / (1000L * 60 * 60 * 24)
        }

    val roi: Double?
        get() = netProfit?.let { profit ->
            val invested = car.purchasePrice + totalExpenses
            if (invested > 0) (profit / invested) * 100.0 else null
        }

    val primaryPhoto: CarPhoto?
        get() = photos.firstOrNull { it.isPrimary } ?: photos.firstOrNull()

    val displayName: String
        get() = "${car.make} ${car.model} ${car.year}"
}

data class DashboardStats(
    val totalProfit: Double         = 0.0,
    val monthProfit: Double         = 0.0,
    val soldCount: Int              = 0,
    val activeCount: Int            = 0,
    val totalExpenses: Double       = 0.0,
    val avgProfitPerCar: Double     = 0.0,
    val topProfitableCars: List<CarWithDetails> = emptyList()
)

data class MonthlyProfit(
    val month: String,
    val profit: Double,
    val expenses: Double,
    val soldCount: Int
)
