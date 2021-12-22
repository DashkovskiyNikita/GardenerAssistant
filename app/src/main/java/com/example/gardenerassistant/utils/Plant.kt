package com.example.gardenerassistant.utils

data class Plant(
    val id : Int?,
    val boardingTime: Long,
    val collectionTime: Long,
    var plantTitle: String,
    val note : String
)
