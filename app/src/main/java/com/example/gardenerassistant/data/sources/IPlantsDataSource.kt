package com.example.gardenerassistant.data.sources

import com.example.gardenerassistant.domain.models.PlantModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream

interface IPlantsDataSource {
    fun fetchAllPlants(): List<PlantModel>
}

class PlantsDataSource(private val stream : InputStream) : IPlantsDataSource {

    private val data : List<PlantModel> by lazy {
        val content = stream.bufferedReader().use { it.readText() }
        Json.decodeFromString(content)
    }

    override fun fetchAllPlants() = data
}
