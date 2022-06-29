package com.example.gardenerassistant.domain.models

import java.time.LocalDate

data class UserPlantModel(
    var id: Int = 0,
    var boardingTime: String = LocalDate.now().toString(),
    var collectionTime: String = "",
    var name: String = "",
    var sort: String = "",
    var note: String = "",
    var image: String? = null
)
