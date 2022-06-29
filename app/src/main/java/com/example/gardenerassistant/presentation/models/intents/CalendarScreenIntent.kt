package com.example.gardenerassistant.presentation.models.intents

sealed class CalendarScreenIntent{
    object PreviousMonthClicked : CalendarScreenIntent()
    object NextMonthClicked : CalendarScreenIntent()
}
