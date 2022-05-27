package com.example.gardenerassistant.presentation.models.states

import com.example.gardenerassistant.domain.models.UserPlantModel

data class HomeScreenState(
    val isLoading : Boolean = false,
    val data : List<UserPlantModel> = emptyList(),
    val error : String = ""
)