package com.example.gardenerassistant.database

import androidx.room.*

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plantEntity: PlantEntity)

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun fetchPlantById(id: Int) : PlantEntity

    @Query("SELECT * FROM plants")
    suspend fun fetchAllPlants() : List<PlantEntity>

    @Query("SELECT * FROM plants WHERE collectionTime >= :start AND collectionTime <= :end")
    suspend fun fetchMonthDays(start : Long,end : Long) : List<PlantEntity>

    @Delete
    suspend fun deletePlant(plantEntity: PlantEntity)

    @Update
    suspend fun updatePlant(plantEntity: PlantEntity)
}