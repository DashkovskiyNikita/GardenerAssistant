package com.example.gardenerassistant.presentation.utils

import com.example.gardenerassistant.R
import java.time.LocalDate

data class MonthDay(
    val value: Int,
    val active: Boolean
)

fun getMonthName(value: Int): Int {
    return when (value) {
        1 -> R.string.january
        2 -> R.string.february
        3 -> R.string.march
        4 -> R.string.april
        5 -> R.string.may
        6 -> R.string.june
        7 -> R.string.july
        8 -> R.string.august
        9 -> R.string.september
        10 -> R.string.october
        11 -> R.string.november
        12 -> R.string.december
        else -> -1
    }
}

fun getMonthDays(date: LocalDate = LocalDate.now()): List<MonthDay> {
    val monthDays = mutableListOf<MonthDay>()
    val firstDay = date.withDayOfMonth(1)
    val dayOfWeek = firstDay.dayOfWeek.value - 1
    val monthLength = firstDay.lengthOfMonth()
    for (i in dayOfWeek downTo 1) {
        val day = firstDay.minusDays(i.toLong())
        monthDays.add(MonthDay(day.dayOfMonth, false))
    }
    for (i in 1..monthLength) {
        monthDays.add(MonthDay(i, true))
    }
    val nextMonthDaysCount = 42 - monthLength - dayOfWeek
    for (i in 0 until nextMonthDaysCount) {
        val day = firstDay.plusDays(i.toLong())
        monthDays.add(MonthDay(day.dayOfMonth, false))
    }
    return monthDays
}