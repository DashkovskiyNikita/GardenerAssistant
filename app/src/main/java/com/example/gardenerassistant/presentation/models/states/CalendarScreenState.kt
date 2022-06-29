package com.example.gardenerassistant.presentation.models.states

import androidx.annotation.StringRes
import com.example.gardenerassistant.domain.models.CalendarPlantModel
import com.example.gardenerassistant.presentation.utils.MonthDay

data class CalendarScreenState(
    @StringRes val monthName: Int? = null,
    val year : Int? = null,
    val isLoading: Boolean = true,
    val monthDays: List<MonthDay> = emptyList(),
    val plantsList: List<CalendarPlantModel> = emptyList()
)
