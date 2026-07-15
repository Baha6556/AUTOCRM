package com.autocrm.data.local.converters

import androidx.room.TypeConverter
import com.autocrm.domain.model.CarStatus
import com.autocrm.domain.model.ExpenseCategory
import com.autocrm.domain.model.EntitySyncStatus
import com.autocrm.domain.model.OperationStatus
import com.autocrm.domain.model.DeliveryStatus

class Converters {
    @TypeConverter fun fromCarStatus(v: CarStatus): String = v.name
    @TypeConverter fun toCarStatus(v: String): CarStatus   = CarStatus.valueOf(v)

    @TypeConverter fun fromEntitySyncStatus(v: EntitySyncStatus): String = v.name
    @TypeConverter fun toEntitySyncStatus(v: String): EntitySyncStatus   = EntitySyncStatus.valueOf(v)

    @TypeConverter fun fromOperationStatus(v: OperationStatus): String = v.name
    @TypeConverter fun toOperationStatus(v: String): OperationStatus   = OperationStatus.valueOf(v)

    @TypeConverter fun fromExpenseCategory(v: ExpenseCategory): String = v.name
    @TypeConverter fun toExpenseCategory(v: String): ExpenseCategory   = ExpenseCategory.valueOf(v)
    
    @TypeConverter fun fromDeliveryStatus(v: DeliveryStatus): String = v.name
    @TypeConverter fun toDeliveryStatus(v: String): DeliveryStatus   = DeliveryStatus.valueOf(v)
}
