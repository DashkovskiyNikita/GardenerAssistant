package com.example.gardenerassistant.domain.repository

import com.example.gardenerassistant.domain.models.UserPlantModel
import kotlinx.coroutines.flow.Flow

interface IUserPlantsRepository {
    suspend fun fetchAllPlants(): Flow<List<UserPlantModel>>
    suspend fun deletePlant(plant: UserPlantModel)
    suspend fun insertPlant(plant: UserPlantModel)
    suspend fun updatePlant(plant: UserPlantModel)
    suspend fun fetchPlantById(id: Int): UserPlantModel
    suspend fun fetchPlantsByMonth(start: String): List<UserPlantModel>
}