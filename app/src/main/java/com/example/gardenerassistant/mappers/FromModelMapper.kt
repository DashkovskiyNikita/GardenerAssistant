package com.example.gardenerassistant.mappers

import com.example.gardenerassistant.database.PlantEntity
import com.example.gardenerassistant.utils.Plant
import javax.inject.Inject

class FromModelMapper @Inject constructor() : Mapper<Plant, PlantEntity> {
    override fun map(input: Plant): PlantEntity {
        return PlantEntity(
            id = input.id ?: 0,
            boardingTime = input.boardingTime,
            collectionTime = input.collectionTime,
            plantTitle = input.plantTitle,
            note = input.note
        )
    }
}