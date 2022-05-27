package com.example.gardenerassistant.data.sources

import com.example.gardenerassistant.data.database.UserPlantEntity
import com.example.gardenerassistant.data.database.UserPlantsDao
import kotlinx.coroutines.flow.Flow

interface IUserPlantsDataSource {
    suspend fun deletePlant(plant: UserPlantEntity)
    suspend fun fetchAllPlants(): Flow<List<UserPlantEntity>>
    suspend fun insertPlant(plant: UserPlantEntity)
    suspend fun updatePlant(plant: UserPlantEntity)
    suspend fun fetchPlantById(id: Int): UserPlantEntity
    suspend fun fetchPlantsByMonth(start: String): List<UserPlantEntity>
}

class UserPlantsDataSource(
    private val plantsDatabase: UserPlantsDao
) : IUserPlantsDataSource {

    override suspend fun deletePlant(plant: UserPlantEntity) = plantsDatabase.deletePlant(plant)

    override suspend fun fetchAllPlants() = plantsDatabase.fetchAllPlants()

    override suspend fun insertPlant(plant: UserPlantEntity) = plantsDatabase.insertPlant(plant)

    override suspend fun updatePlant(plant: UserPlantEntity) = plantsDatabase.updatePlant(plant)

    override suspend fun fetchPlantById(id: Int) = plantsDatabase.fetchPlantById(id)

    override suspend fun fetchPlantsByMonth(start: String) = plantsDatabase.fetchPlantsByMonth(start)

}