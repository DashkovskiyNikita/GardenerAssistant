package com.example.gardenerassistant.domain.usecases

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<in Param, out Data> : UseCase<Param, Flow<Data>>

interface UseCase<in Param, out Data> {
    suspend operator fun invoke(param: Param): Data
}