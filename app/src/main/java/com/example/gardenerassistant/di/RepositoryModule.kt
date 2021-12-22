package com.example.gardenerassistant.di

import com.example.gardenerassistant.database.PlantDao
import com.example.gardenerassistant.mappers.FromEntityMapper
import com.example.gardenerassistant.mappers.FromModelMapper
import com.example.gardenerassistant.repository.PlantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        plantDao: PlantDao,
        fromModelMapper: FromModelMapper,
        fromEntityMapper: FromEntityMapper
    ): PlantRepository {
        return PlantRepository(
            plantDao = plantDao,
            fromModelMapper = fromModelMapper,
            fromEntityMapper = fromEntityMapper
        )
    }
}