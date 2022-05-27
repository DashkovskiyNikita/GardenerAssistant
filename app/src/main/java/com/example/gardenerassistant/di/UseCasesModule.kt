package com.example.gardenerassistant.di

import com.example.gardenerassistant.domain.usecases.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCasesModule = module {
    singleOf(::CreateUserPlantUseCase) {
        bind<ICreateUserPlantUseCase>()
    }
    singleOf(::DeleteUserPlantUseCase) {
        bind<IDeleteUserPlantUseCase>()
    }
    singleOf(::GetUserPlantsByMonthUseCase) {
        bind<IGetUserPlantsByMonthUseCase>()
    }
    singleOf(::GetUserPlantsUseCase) {
        bind<IGetUserPlantsUseCase>()
    }
    singleOf(::GetUserPlantUseCase) {
        bind<IGetUserPlantUseCase>()
    }
    singleOf(::UpdateUserPlantUseCase) {
        bind<IUpdateUserPlantUseCase>()
    }
}