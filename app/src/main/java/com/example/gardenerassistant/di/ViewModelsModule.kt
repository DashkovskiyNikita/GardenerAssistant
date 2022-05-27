package com.example.gardenerassistant.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import com.example.gardenerassistant.presentation.viewmodels.*
import org.koin.core.module.dsl.bind
import org.koin.dsl.module

val viewModelsModule = module {
    viewModelOf(::CalendarScreenViewModel) {
        bind<ICalendarScreenViewModel>()
    }
    viewModelOf(::HomeScreenViewModel) {
        bind<IHomeScreenViewModel>()
    }
    viewModelOf(::UserPlantViewModel) {
        bind<IUserPlantViewModel>()
    }
}