package com.example.gardenerassistant.usecase

import com.example.gardenerassistant.repository.PlantRepository
import javax.inject.Inject

class FetchPlantByIdUseCase @Inject constructor(private val plantRepository: PlantRepository) {

    suspend operator fun invoke(id : Int) = plantRepository.fetchPlantById(id)
}