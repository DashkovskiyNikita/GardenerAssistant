package com.example.gardenerassistant.presentation.models.states

import android.graphics.Bitmap
import com.example.gardenerassistant.domain.models.UserPlantModel

data class UserPlantState(
    val userPlantModel: UserPlantModel = UserPlantModel(),
    val plantsSuggestions: List<String> = emptyList(),
    val sortsSuggestions: List<String> = emptyList(),
    val bitmapLoading: Boolean = false,
    val bitmap: Bitmap? = null
)
