package com.example.gardenerassistant.domain.usecases

import com.example.gardenerassistant.domain.models.UserPlantModel
import com.example.gardenerassistant.domain.repository.IUserPlantsRepository

interface ICreateUserPlantUseCase : UseCase<UserPlantModel, Unit>

class CreateUserPlantUseCase(
    private val plantsRepository: IUserPlantsRepository
) : ICreateUserPlantUseCase {
    override suspend fun invoke(param: UserPlantModel) = plantsRepository.insertPlant(param)
}