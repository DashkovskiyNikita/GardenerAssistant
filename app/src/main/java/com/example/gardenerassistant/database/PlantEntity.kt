package com.example.gardenerassistant.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PlantEntity.tableName)
data class PlantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "boardingTime")
    val boardingTime: Long,
    @ColumnInfo(name = "collectionTime")
    val collectionTime: Long,
    @ColumnInfo(name = "plantTitle")
    val plantTitle: String,
    @ColumnInfo(name = "note")
    val note : String
) {
    companion object {
        const val tableName = "plants"
    }
}
