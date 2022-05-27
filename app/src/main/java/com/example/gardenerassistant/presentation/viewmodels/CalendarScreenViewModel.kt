package com.example.gardenerassistant.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.gardenerassistant.domain.usecases.IGetUserPlantsByMonthUseCase
import com.example.gardenerassistant.presentation.models.intents.CalendarScreenIntent
import com.example.gardenerassistant.presentation.models.states.CalendarScreenState
import com.example.gardenerassistant.presentation.utils.getMonthDays
import com.example.gardenerassistant.presentation.utils.getMonthName
import kotlinx.coroutines.launch
import java.time.LocalDate

abstract class ICalendarScreenViewModel(
    initialState: CalendarScreenState
) : MVIViewModel<CalendarScreenState>(initialState), EventListener<CalendarScreenIntent>

interface EventListener<in Event : Any> {
    fun onEvent(event: Event)
}

class CalendarScreenViewModel(
    private val getUserPlantsByMonthUseCase: IGetUserPlantsByMonthUseCase
) : ICalendarScreenViewModel(CalendarScreenState()) {

    private var date = LocalDate.now().withDayOfMonth(1)

    init {
        fetchPlantsByMonth()
    }

    override fun onEvent(event: CalendarScreenIntent) {
        when (event) {
            is CalendarScreenIntent.NextMonthClicked -> {
                date = date.plusMonths(1)
                fetchPlantsByMonth()
            }
            is CalendarScreenIntent.PreviousMonthClicked -> {
                date = date.minusMonths(1)
                fetchPlantsByMonth()
            }
        }
    }

    private fun fetchPlantsByMonth() {
        changeMonthState()
        viewModelScope.launch {
            val userPlants = getUserPlantsByMonthUseCase(date.toString())
            reduce { state.copy(isLoading = false, plantsList = userPlants) }
        }
    }

    private fun changeMonthState() = reduce {
        state.copy(
            plantsList = emptyList(),
            isLoading = true,
            monthName = getMonthName(date.month.value),
            monthDays = getMonthDays(date),
            year = date.year
        )
    }
}