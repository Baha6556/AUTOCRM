package com.autocrm.domain.repository

import com.autocrm.domain.model.*
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    fun getAllCars(): Flow<List<CarWithDetails>>
    fun getSoldCars(): Flow<List<CarWithDetails>>
    fun getCarsByStatus(status: CarStatus): Flow<List<CarWithDetails>>
    fun searchCars(query: String): Flow<List<CarWithDetails>>
    suspend fun getCarById(uuid: String): CarWithDetails?
    fun getCarByIdFlow(uuid: String): Flow<CarWithDetails>
    suspend fun saveCar(car: Car)
    suspend fun deleteCar(uuid: String)
    fun getDashboardStats(): Flow<DashboardStats>
    fun getMonthlyProfits(): Flow<List<MonthlyProfit>>

    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(uuid: String)

    suspend fun addPhoto(photo: CarPhoto)
    suspend fun deletePhoto(uuid: String)
    suspend fun setPrimaryPhoto(carUuid: String, photoUuid: String)
}
