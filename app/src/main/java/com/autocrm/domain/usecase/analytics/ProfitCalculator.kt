package com.autocrm.domain.usecase.analytics

import com.autocrm.domain.model.CarStatus
import com.autocrm.domain.model.CarWithDetails
import com.autocrm.domain.model.DashboardStats
import com.autocrm.domain.model.MonthlyProfit
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ProfitCalculator @Inject constructor() {
    fun calculateDashboardStats(cars: List<CarWithDetails>): DashboardStats {
        val sold = cars.filter { it.car.status == CarStatus.SOLD }
        val active = cars.filter { it.car.status != CarStatus.SOLD && !it.car.isDeleted }

        val monthStart = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val totalProfit = sold.sumOf { it.netProfit ?: 0.0 }
        val monthProfit = sold
            .filter { (it.car.saleDate ?: 0L) >= monthStart }
            .sumOf { it.netProfit ?: 0.0 }
        val totalExpenses = cars.sumOf { it.totalExpenses }
        val avgProfit = if (sold.isNotEmpty()) totalProfit / sold.size else 0.0
        val top = sold.sortedByDescending { it.netProfit ?: 0.0 }.take(5)

        return DashboardStats(
            totalProfit    = totalProfit,
            monthProfit    = monthProfit,
            soldCount      = sold.size,
            activeCount    = active.size,
            totalExpenses  = totalExpenses,
            avgProfitPerCar = avgProfit,
            topProfitableCars = top
        )
    }

    fun calculateMonthlyProfits(soldCars: List<CarWithDetails>): List<MonthlyProfit> {
        val fmt = SimpleDateFormat("MMM yyyy", Locale("ru"))
        return soldCars.groupBy { car ->
            car.car.saleDate?.let { fmt.format(Date(it)) } ?: "Неизвестно"
        }.map { (month, cars) ->
            MonthlyProfit(
                month      = month,
                profit     = cars.sumOf { it.netProfit ?: 0.0 },
                expenses   = cars.sumOf { it.totalExpenses },
                soldCount  = cars.size
            )
        }.sortedByDescending { it.month }.take(12).reversed()
    }
}
