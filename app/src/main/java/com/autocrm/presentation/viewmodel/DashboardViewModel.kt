package com.autocrm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autocrm.domain.model.CarStatus
import com.autocrm.domain.repository.CarRepository
import com.autocrm.domain.usecase.analytics.ProfitCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class TopCarUiModel(
    val uuid: String,
    val model: String,
    val photoUrl: String,
    val profit: Double,
    val roi: Double
)

data class DashboardState(
    val totalProfit: Double = 145200.0,
    val carsInStock: Int = 12,
    val carsSold: Int = 4,
    val carsInTransit: Int = 8,
    val topCars: List<TopCarUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val stats: com.autocrm.domain.model.DashboardStats = com.autocrm.domain.model.DashboardStats(),
    val monthlyProfits: List<com.autocrm.domain.model.MonthlyProfit> = emptyList()
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val profitCalculator: ProfitCalculator
) : ViewModel() {

    val dashboardState: StateFlow<DashboardState> = carRepository.getAllCars()
        .map { cars ->
            val stats = profitCalculator.calculateDashboardStats(cars)
            
            val inStock = cars.count { it.car.status != CarStatus.SOLD && it.car.status != CarStatus.IN_TRANSIT && !it.car.isDeleted }
            val inTransit = cars.count { it.car.status == CarStatus.IN_TRANSIT && !it.car.isDeleted }
            val sold = cars.count { it.car.status == CarStatus.SOLD && !it.car.isDeleted }

            val topCarsUi = stats.topProfitableCars.map { carWithDetails ->
                TopCarUiModel(
                    uuid = carWithDetails.car.uuid,
                    model = "${carWithDetails.car.make} ${carWithDetails.car.model}",
                    photoUrl = "https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8", // Fallback URL
                    profit = carWithDetails.netProfit ?: 0.0,
                    roi = carWithDetails.roi ?: 0.0
                )
            }

            DashboardState(
                totalProfit = stats.monthProfit, // Use monthProfit for Hero Card
                carsInStock = inStock,
                carsSold = sold,
                carsInTransit = inTransit,
                topCars = topCarsUi,
                stats = stats,
                monthlyProfits = profitCalculator.calculateMonthlyProfits(cars.filter { it.car.status == CarStatus.SOLD })
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardState()
        )
}
