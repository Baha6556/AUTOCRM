package com.autocrm.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autocrm.domain.model.CarStatus
import com.autocrm.domain.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class TimelineEventUiModel(val title: String, val date: String, val cost: Double? = null)

data class CarDetailState(
    val uuid: String = "",
    val make: String = "Mercedes-Benz",
    val model: String = "E-Class AMG",
    val status: String = "In Transit",
    val photos: List<String> = emptyList(),
    val totalSpent: Double = 45000.0,
    val estimatedProfit: Double = 15000.0,
    val roi: Double = 33.3,
    val timelineEvents: List<TimelineEventUiModel> = emptyList()
)

@HiltViewModel
class CarDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val carRepository: CarRepository
) : ViewModel() {

    private val carUuid: String = checkNotNull(savedStateHandle["carUuid"])
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    val carState: StateFlow<CarDetailState> = carRepository.getCarByIdFlow(carUuid)
        .filterNotNull()
        .map { carWithDetails ->
            val expenses = carWithDetails.expenses.map { expense ->
                TimelineEventUiModel(
                    title = expense.category.name,
                    date = dateFormat.format(Date(expense.date)),
                    cost = expense.amount
                )
            }

            val timeline = mutableListOf<TimelineEventUiModel>()
            carWithDetails.car.purchaseDate?.let {
                timeline.add(TimelineEventUiModel("Purchased", dateFormat.format(Date(it)), carWithDetails.car.purchasePrice))
            }
            timeline.addAll(expenses)
            if (carWithDetails.car.status == CarStatus.SOLD && carWithDetails.car.saleDate != null) {
                timeline.add(TimelineEventUiModel("Sold", dateFormat.format(Date(carWithDetails.car.saleDate)), carWithDetails.car.salePrice))
            }

            CarDetailState(
                uuid = carWithDetails.car.uuid,
                make = carWithDetails.car.make,
                model = carWithDetails.car.model,
                status = carWithDetails.car.status.name.replace("_", " "),
                photos = carWithDetails.photos.map { it.filePath },
                totalSpent = carWithDetails.car.purchasePrice + carWithDetails.totalExpenses,
                estimatedProfit = carWithDetails.estimatedProfit ?: 0.0,
                roi = carWithDetails.roi ?: 0.0,
                timelineEvents = timeline.sortedBy { it.date } // Roughly sorted by string for UI simplicity
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CarDetailState(uuid = carUuid)
        )
}
