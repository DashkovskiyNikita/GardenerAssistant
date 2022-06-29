package com.example.gardenerassistant.domain.usecases

import com.example.gardenerassistant.domain.models.UserPlantModel
import com.example.gardenerassistant.domain.repository.IUserPlantsRepository

interface IDeleteUserPlantUseCase : UseCase<UserPlantModel, Unit>

class DeleteUserPlantUseCase(
    private val plantsRepository: IUserPlantsRepository
) : IDeleteUserPlantUseCase {
    override suspend fun invoke(param: UserPlantModel) = plantsRepository.deletePlant(param)
}