package com.example.gardenerassistant.di

import org.koin.core.module.dsl.singleOf
import com.example.gardenerassistant.data.mappers.*
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val mappersModule = module {
    singleOf(::PlantModelToEntityMapper) {
        bind<IPlantModelToEntityMapper>()
    }
    singleOf(::EntityToPlantModelMapper) {
        bind<IEntityToPlantModelMapper>()
    }
    singleOf(::CalendarPlantModelMapper) {
        bind<ICalendarPlantModelMapper>()
    }
}