package com.example.gardenerassistant.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPlantsDao {
    @Query("SELECT * FROM userPlants WHERE collectionTime BETWEEN DATE(:start) AND DATE(:start,'+1 month','-1 day')")
    suspend fun fetchPlantsByMonth(start: String): List<UserPlantEntity>

    @Query("SELECT * FROM userPlants")
    fun fetchAllPlants(): Flow<List<UserPlantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: UserPlantEntity)

    @Query("SELECT * FROM userPlants WHERE id = :id")
    suspend fun fetchPlantById(id: Int): UserPlantEntity

    @Update
    suspend fun updatePlant(plant: UserPlantEntity)

    @Delete
    suspend fun deletePlant(plant: UserPlantEntity)
}