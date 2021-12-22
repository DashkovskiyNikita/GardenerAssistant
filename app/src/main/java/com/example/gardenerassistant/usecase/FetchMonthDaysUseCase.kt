package com.example.gardenerassistant.usecase

import com.example.gardenerassistant.repository.PlantRepository
import com.example.gardenerassistant.utils.Plant
import javax.inject.Inject

class FetchMonthDaysUseCase @Inject constructor(private val plantRepository: PlantRepository) {
    suspend operator fun invoke(start: Long, end: Long): List<Plant> {
        return plantRepository.fetchMonthDays(start, end)
    }
}