package com.example.gardenerassistant.domain.usecases

import com.example.gardenerassistant.domain.models.UserPlantModel
import com.example.gardenerassistant.domain.repository.IUserPlantsRepository

interface IUpdateUserPlantUseCase : UseCase<UserPlantModel, Unit>

class UpdateUserPlantUseCase(
    private val plantsRepository: IUserPlantsRepository
) : IUpdateUserPlantUseCase {
    override suspend fun invoke(param: UserPlantModel) = plantsRepository.updatePlant(param)
}