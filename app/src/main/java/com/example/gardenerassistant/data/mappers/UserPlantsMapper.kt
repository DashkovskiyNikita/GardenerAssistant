package com.example.gardenerassistant.data.mappers

import com.example.gardenerassistant.data.database.UserPlantEntity
import com.example.gardenerassistant.domain.models.CalendarPlantModel
import com.example.gardenerassistant.domain.models.UserPlantModel
import java.time.LocalDate

interface IPlantModelToEntityMapper : Mapper<UserPlantModel, UserPlantEntity>

interface IEntityToPlantModelMapper : Mapper<UserPlantEntity, UserPlantModel>

interface ICalendarPlantModelMapper : Mapper<UserPlantModel, CalendarPlantModel>

class PlantModelToEntityMapper : IPlantModelToEntityMapper {
    override fun invoke(input: UserPlantModel): UserPlantEntity {
        return UserPlantEntity(
            id = input.id,
            boardingTime = input.boardingTime,
            collectionTime = input.collectionTime,
            name = input.name,
            sort = input.sort,
            note = input.note,
            image = input.image
        )
    }
}

class EntityToPlantModelMapper : IEntityToPlantModelMapper {
    override fun invoke(input: UserPlantEntity): UserPlantModel {
        return UserPlantModel(
            id = input.id,
            boardingTime = input.boardingTime,
            collectionTime = input.collectionTime,
            name = input.name,
            sort = input.sort,
            note = input.note,
            image = input.image
        )
    }
}

class CalendarPlantModelMapper : ICalendarPlantModelMapper {
    override fun invoke(input: UserPlantModel): CalendarPlantModel {
        return CalendarPlantModel(
            name = input.name,
            monthDay = LocalDate.parse(input.collectionTime).dayOfMonth
        )
    }
}