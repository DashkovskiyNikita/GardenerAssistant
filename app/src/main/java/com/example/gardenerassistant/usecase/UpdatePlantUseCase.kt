package com.example.gardenerassistant.usecase

import com.example.gardenerassistant.repository.PlantRepository
import com.example.gardenerassistant.utils.Plant
import javax.inject.Inject

class UpdatePlantUseCase @Inject constructor(private val plantRepository: PlantRepository) {

    suspend operator fun invoke(plant: Plant) = plantRepository.updatePlant(plant)
}