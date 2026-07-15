package com.autocrm.domain.usecase.analytics

import com.autocrm.domain.model.CarWithDetails
import com.autocrm.domain.model.ExpenseCategory
import javax.inject.Inject

class ExpenseAnalyticsService @Inject constructor() {
    
    fun calculateTotalExpensesByCategory(cars: List<CarWithDetails>): Map<ExpenseCategory, Double> {
        val expenseMap = mutableMapOf<ExpenseCategory, Double>()
        
        cars.forEach { carDetails ->
            carDetails.expenses.forEach { expense ->
                val current = expenseMap[expense.category] ?: 0.0
                expenseMap[expense.category] = current + expense.amount
            }
        }
        
        return expenseMap
    }
    
    fun getAverageExpensePerCar(cars: List<CarWithDetails>): Double {
        if (cars.isEmpty()) return 0.0
        val total = cars.sumOf { it.totalExpenses }
        return total / cars.size
    }
}
