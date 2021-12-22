package com.example.gardenerassistant.repository

import com.example.gardenerassistant.database.PlantDao

import com.example.gardenerassistant.mappers.FromEntityMapper
import com.example.gardenerassistant.mappers.FromModelMapper
import com.example.gardenerassistant.utils.Plant

class PlantRepository(
    private val plantDao: PlantDao,
    private val fromEntityMapper: FromEntityMapper,
    private val fromModelMapper: FromModelMapper
) {
    suspend fun insertPlant(plant: Plant) {
        val entity = fromModelMapper.map(plant)
        plantDao.insertPlant(entity)
    }

    suspend fun fetchAllPlants(): List<Plant> {
        return plantDao.fetchAllPlants().map { fromEntityMapper.map(it) }
    }

    suspend fun fetchPlantById(id: Int): Plant {
        val entity = plantDao.fetchPlantById(id)
        return fromEntityMapper.map(entity)
    }

    suspend fun deletePlant(plant: Plant) {
        val entity = fromModelMapper.map(plant)
        plantDao.deletePlant(entity)
    }

    suspend fun updatePlant(plant: Plant) {
        val entity = fromModelMapper.map(plant)
        plantDao.updatePlant(entity)
    }

    suspend fun fetchMonthDays(start: Long, end: Long): List<Plant> {
        return plantDao.fetchMonthDays(start, end).map { fromEntityMapper.map(it) }
    }
}