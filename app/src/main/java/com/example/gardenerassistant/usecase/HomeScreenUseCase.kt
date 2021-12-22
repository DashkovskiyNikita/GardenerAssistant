package com.example.gardenerassistant.usecase

import com.example.gardenerassistant.repository.PlantRepository
import com.example.gardenerassistant.states.DataState
import com.example.gardenerassistant.utils.Plant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class FetchAllPlantsUseCase @Inject constructor(private val plantRepository: PlantRepository) {
    operator fun invoke(): Flow<DataState<List<Plant>>> = flow {
        try {
            emit(DataState.Loading())
            val plantList = plantRepository.fetchAllPlants()
            emit(DataState.Success(plantList))
        } catch (ex: IOException){
            emit(DataState.Error(ex.message.toString()))
        }
    }
}