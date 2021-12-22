package com.example.gardenerassistant.di

import android.content.Context
import androidx.room.Room
import com.example.gardenerassistant.database.PlantDao
import com.example.gardenerassistant.database.PlantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): PlantDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PlantDatabase::class.java,
            PlantDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDao(plantDatabase: PlantDatabase): PlantDao {
        return plantDatabase.plantDao()
    }
}