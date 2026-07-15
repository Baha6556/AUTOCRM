package com.autocrm.domain.usecase.analytics

import com.autocrm.domain.model.CarWithDetails
import javax.inject.Inject

class ROIService @Inject constructor() {
    
    fun calculateROI(car: CarWithDetails): Double? {
        val netProfit = car.netProfit ?: return null
        val invested = car.car.purchasePrice + car.totalExpenses
        
        if (invested <= 0.0) return null
        
        return (netProfit / invested) * 100.0
    }

    fun calculateEstimatedROI(car: CarWithDetails): Double? {
        val estimatedProfit = car.estimatedProfit ?: return null
        val invested = car.car.purchasePrice + car.totalExpenses
        
        if (invested <= 0.0) return null
        
        return (estimatedProfit / invested) * 100.0
    }
}
