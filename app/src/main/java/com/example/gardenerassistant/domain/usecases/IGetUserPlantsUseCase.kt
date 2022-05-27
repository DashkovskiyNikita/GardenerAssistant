package com.example.gardenerassistant.domain.usecases

import com.example.gardenerassistant.domain.models.UserPlantModel
import com.example.gardenerassistant.domain.repository.IUserPlantsRepository

interface IGetUserPlantsUseCase : FlowUseCase<Unit, List<UserPlantModel>>

class GetUserPlantsUseCase(
    private val plantsRepository: IUserPlantsRepository
) : IGetUserPlantsUseCase {
    override suspend fun invoke(param: Unit) = plantsRepository.fetchAllPlants()
}