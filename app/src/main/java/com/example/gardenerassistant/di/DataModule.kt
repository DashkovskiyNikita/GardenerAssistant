package com.example.gardenerassistant.di

import androidx.room.Room
import com.example.gardenerassistant.data.database.PlantsDatabase
import com.example.gardenerassistant.data.*
import com.example.gardenerassistant.data.sources.*
import com.example.gardenerassistant.domain.repository.IUserPlantsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            PlantsDatabase::class.java,
            PlantsDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build().plantsDao()
    }
    single {
        androidContext().assets.open("plantsInfo.txt")
    }
    singleOf(::ImageStorage) {
        bind<AbstractImageStorage>()
    }
    singleOf(::PlantsDataSource) {
        bind<IPlantsDataSource>()
    }
    singleOf(::UserPlantsDataSource) {
        bind<IUserPlantsDataSource>()
    }
    singleOf(::UserPlantsRepository) {
        bind<IUserPlantsRepository>()
    }
}