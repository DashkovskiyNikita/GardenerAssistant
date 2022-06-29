package com.example.gardenerassistant.presentation.models.intents

sealed class UserPlantScreenIntent {
    data class NameChanged(val value: String) : UserPlantScreenIntent()
    data class SortChanged(val value: String) : UserPlantScreenIntent()
    data class NoteChanged(val value: String) : UserPlantScreenIntent()
    data class BoardingTimeChanged(val value: String) : UserPlantScreenIntent()
    data class CollectionTimeChanged(val value: String) : UserPlantScreenIntent()
}
