package com.example.gardenerassistant.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserPlantEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PlantsDatabase : RoomDatabase() {

    abstract fun plantsDao(): UserPlantsDao

    companion object {
        const val DB_NAME = "plants_db"
    }
}