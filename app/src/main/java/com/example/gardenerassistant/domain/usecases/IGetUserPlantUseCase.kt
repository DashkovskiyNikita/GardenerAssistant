package com.example.gardenerassistant.domain.usecases

import com.example.gardenerassistant.domain.models.UserPlantModel
import com.example.gardenerassistant.domain.repository.IUserPlantsRepository

interface IGetUserPlantUseCase : UseCase<Int, UserPlantModel>

class GetUserPlantUseCase(
    private val plantsRepository: IUserPlantsRepository
) : IGetUserPlantUseCase {
    override suspend fun invoke(param: Int) = plantsRepository.fetchPlantById(param)
}