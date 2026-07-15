package com.autocrm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autocrm.domain.model.CarStatus
import com.autocrm.domain.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortBy(val label: String) {
    DATE_DESC("Сначала новые"),
    DATE_ASC("Сначала старые"),
    PRICE_DESC("Сначала дорогие"),
    PRICE_ASC("Сначала дешевые")
}

data class CarsUiState(
    val cars: List<com.autocrm.domain.model.CarWithDetails> = emptyList(),
    val searchQuery: String = "",
    val selectedStatus: com.autocrm.domain.model.CarStatus? = null,
    val sortBy: SortBy = SortBy.DATE_DESC
)

@HiltViewModel
class CarsViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    private val selectedFilter = MutableStateFlow<com.autocrm.domain.model.CarStatus?>(null)
    private val sortBy = MutableStateFlow(SortBy.DATE_DESC)

    val carsUiState: StateFlow<CarsUiState> = combine(
        carRepository.getAllCars(),
        searchQuery,
        selectedFilter,
        sortBy
    ) { cars, query, filter, sort ->
        val filteredCars = cars.filter { carWithDetails ->
            val matchesQuery = carWithDetails.car.make.contains(query, ignoreCase = true) ||
                               carWithDetails.car.model.contains(query, ignoreCase = true) ||
                               (carWithDetails.car.vin?.contains(query, ignoreCase = true) == true)
            
            val matchesFilter = filter == null || carWithDetails.car.status == filter

            matchesQuery && matchesFilter && !carWithDetails.car.isDeleted
        }
        
        val sortedCars = when(sort) {
            SortBy.DATE_DESC -> filteredCars.sortedByDescending { it.car.purchaseDate }
            SortBy.DATE_ASC -> filteredCars.sortedBy { it.car.purchaseDate }
            SortBy.PRICE_DESC -> filteredCars.sortedByDescending { it.car.purchasePrice }
            SortBy.PRICE_ASC -> filteredCars.sortedBy { it.car.purchasePrice }
        }

        CarsUiState(
            cars = sortedCars,
            searchQuery = query,
            selectedStatus = filter,
            sortBy = sort
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CarsUiState()
    )

    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun updateFilter(filter: com.autocrm.domain.model.CarStatus?) {
        selectedFilter.value = filter
    }

    fun setSortBy(sort: SortBy) {
        sortBy.value = sort
    }

    private val _selectedCar = MutableStateFlow<com.autocrm.domain.model.CarWithDetails?>(null)
    val selectedCar: StateFlow<com.autocrm.domain.model.CarWithDetails?> = _selectedCar

    fun loadCar(uuid: String) {
        viewModelScope.launch {
            _selectedCar.value = carRepository.getCarById(uuid)
        }
    }

    fun saveCar(car: com.autocrm.domain.model.Car) {
        viewModelScope.launch {
            carRepository.saveCar(car)
        }
    }

    fun addPhoto(carUuid: String, uri: String, isPrimary: Boolean) {
        viewModelScope.launch {
            carRepository.addPhoto(
                com.autocrm.domain.model.CarPhoto(
                    uuid = java.util.UUID.randomUUID().toString(),
                    carUuid = carUuid,
                    filePath = uri,
                    remoteUrl = null,
                    isPrimary = isPrimary,
                    sortOrder = 0,
                    syncStatus = com.autocrm.domain.model.EntitySyncStatus.PENDING
                )
            )
        }
    }

    fun addExpense(expense: com.autocrm.domain.model.Expense) {
        viewModelScope.launch {
            carRepository.addExpense(expense)
        }
    }

    fun deleteExpense(uuid: String) {
        viewModelScope.launch {
            carRepository.deleteExpense(uuid)
        }
    }

    fun markAsSold(uuid: String, price: Double) {
        viewModelScope.launch {
            val carWithDetails = carRepository.getCarById(uuid)
            if (carWithDetails != null) {
                val updatedCar = carWithDetails.car.copy(
                    status = CarStatus.SOLD,
                    salePrice = price,
                    saleDate = System.currentTimeMillis()
                )
                carRepository.saveCar(updatedCar)
                _selectedCar.value = carRepository.getCarById(uuid)
            }
        }
    }

    fun deleteCar(uuid: String) {
        viewModelScope.launch {
            carRepository.deleteCar(uuid)
        }
    }
}
