package com.example.gardenerassistant.states

import com.example.gardenerassistant.utils.Plant

data class HomeScreenState(
    val isLoading : Boolean = false,
    val data : List<Plant> = emptyList(),
    val error : String = ""
)