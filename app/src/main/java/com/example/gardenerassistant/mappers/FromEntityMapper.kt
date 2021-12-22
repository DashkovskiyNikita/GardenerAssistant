package com.example.gardenerassistant.mappers

import com.example.gardenerassistant.database.PlantEntity
import com.example.gardenerassistant.utils.Plant
import javax.inject.Inject

class FromEntityMapper @Inject constructor() : Mapper<PlantEntity, Plant> {
    override fun map(input: PlantEntity): Plant {
        return Plant(
            id = input.id,
            boardingTime = input.boardingTime,
            collectionTime = input.collectionTime,
            plantTitle = input.plantTitle,
            note = input.note
        )
    }
}