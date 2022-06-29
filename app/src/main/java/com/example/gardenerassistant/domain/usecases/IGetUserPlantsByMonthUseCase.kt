package com.example.gardenerassistant.domain.usecases

import com.example.gardenerassistant.data.mappers.CalendarPlantModelMapper
import com.example.gardenerassistant.domain.models.CalendarPlantModel
import com.example.gardenerassistant.domain.repository.IUserPlantsRepository

interface IGetUserPlantsByMonthUseCase : UseCase<String, List<CalendarPlantModel>>

class GetUserPlantsByMonthUseCase(
    private val plantsRepository: IUserPlantsRepository,
    private val calendarPlantModelMapper: CalendarPlantModelMapper
) : IGetUserPlantsByMonthUseCase {
    override suspend fun invoke(param: String) =
        plantsRepository.fetchPlantsByMonth(param).map(calendarPlantModelMapper::invoke)
}