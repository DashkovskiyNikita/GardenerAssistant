package com.example.gardenerassistant.data

import com.example.gardenerassistant.data.mappers.IEntityToPlantModelMapper
import com.example.gardenerassistant.data.mappers.IPlantModelToEntityMapper
import com.example.gardenerassistant.data.sources.UserPlantsDataSource
import com.example.gardenerassistant.domain.repository.IUserPlantsRepository
import com.example.gardenerassistant.domain.models.UserPlantModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPlantsRepository(
    private val plantsDataSource: UserPlantsDataSource,
    private val toPlantModelMapper: IEntityToPlantModelMapper,
    private val toEntityMapper: IPlantModelToEntityMapper
) : IUserPlantsRepository {

    override suspend fun fetchAllPlants(): Flow<List<UserPlantModel>> {
        return plantsDataSource.fetchAllPlants().map { plants ->
            plants.map(toPlantModelMapper::invoke)
        }
    }

    override suspend fun deletePlant(plant: UserPlantModel) {
        val entity = toEntityMapper(plant)
        plantsDataSource.deletePlant(entity)
    }

    override suspend fun insertPlant(plant: UserPlantModel) {
        val entity = toEntityMapper(plant)
        plantsDataSource.insertPlant(entity)
    }

    override suspend fun updatePlant(plant: UserPlantModel) {
        val entity = toEntityMapper(plant)
        plantsDataSource.updatePlant(entity)
    }

    override suspend fun fetchPlantById(id: Int) =
        plantsDataSource.fetchPlantById(id).let(toPlantModelMapper::invoke)

    override suspend fun fetchPlantsByMonth(start: String) =
        plantsDataSource.fetchPlantsByMonth(start).map(toPlantModelMapper::invoke)

}