package com.example.gardenerassistant.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserPlantEntity.tableName)
data class UserPlantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val boardingTime: String,
    val collectionTime: String,
    val name: String,
    val sort: String,
    val note: String,
    val image: String?
) {
    companion object {
        const val tableName = "userPlants"
    }
}
