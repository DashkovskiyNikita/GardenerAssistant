package com.example.gardenerassistant.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PlantModel(
    val name: String,
    val sort: String,
    val growingTime: Int
)
