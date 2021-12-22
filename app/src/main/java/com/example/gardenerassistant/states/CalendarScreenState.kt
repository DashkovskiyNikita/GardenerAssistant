package com.example.gardenerassistant.states

import com.example.gardenerassistant.utils.Plant

data class CalendarScreenState(
    val isLoading : Boolean = false,
    val data : List<Plant> = emptyList()
)
