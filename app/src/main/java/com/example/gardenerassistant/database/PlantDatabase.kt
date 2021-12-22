package com.example.gardenerassistant.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlantEntity::class], version = 1,exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao

    companion object {
        const val DB_NAME = "plants_db"
    }
}