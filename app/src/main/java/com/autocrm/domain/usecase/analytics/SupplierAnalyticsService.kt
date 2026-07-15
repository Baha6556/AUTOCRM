package com.autocrm.domain.usecase.analytics

import com.autocrm.domain.model.CarStatus
import com.autocrm.domain.model.Supplier
import com.autocrm.domain.repository.CarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class SupplierPerformance(
    val supplier: Supplier,
    val totalCarsBought: Int,
    val totalCarsSold: Int,
    val totalProfitGenerated: Double,
    val avgProfitPerCar: Double
)

class SupplierAnalyticsService @Inject constructor(
    private val carRepository: CarRepository
) {
    fun getSupplierPerformance(supplier: Supplier): Flow<SupplierPerformance> {
        return carRepository.getAllCars().map { allCars ->
            val supplierCars = allCars.filter { it.car.supplierUuid == supplier.uuid }
            val soldCars = supplierCars.filter { it.car.status == CarStatus.SOLD }
            
            val totalProfit = soldCars.sumOf { it.netProfit ?: 0.0 }
            val avgProfit = if (soldCars.isNotEmpty()) totalProfit / soldCars.size else 0.0
            
            SupplierPerformance(
                supplier = supplier,
                totalCarsBought = supplierCars.size,
                totalCarsSold = soldCars.size,
                totalProfitGenerated = totalProfit,
                avgProfitPerCar = avgProfit
            )
        }
    }
}
