package com.example.gardenerassistant.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset
import java.util.*
import java.util.concurrent.TimeUnit


data class Day(val dayInMilli: Long, val dayNumber: Int, var isActive: Boolean)

object DateHelper {

    private val months =
        hashMapOf(
            1 to "Январь",
            2 to "Февраль",
            3 to "Март",
            4 to "Апрель",
            5 to "Май",
            6 to "Июнь",
            7 to "Июль",
            8 to "Август",
            9 to "Сентябрь",
            10 to "Октябрь",
            11 to "Ноябрь",
            12 to "Декабрь"
        )

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(pattern: String): String {
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(Date())
    }

    fun getMonthNameByNumber(month: Int) = months[month]

    fun getFirstDayMillisByLocalDate(localDate: LocalDate) : Long{
        val firstDayLocalDate = YearMonth.from(localDate).atDay(1)
        return firstDayLocalDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    fun getLastDayMillisByNumber(localDate: LocalDate) : Long{
        val lastDayLocalDate = YearMonth.from(localDate).atEndOfMonth()
        return lastDayLocalDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    fun getDay(millis : Long) : Int{
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = millis
        return calendar.get(Calendar.DAY_OF_MONTH)
    }
    fun getMonthDaysByDate(currentDate: LocalDate = LocalDate.now()): List<Day> {

        val daysOfMonth = mutableListOf<Day>()
        val firstMonthDay = YearMonth.from(currentDate).atDay(1)
        val monthLength = YearMonth.from(currentDate).lengthOfMonth()
        val dayOfWeek = firstMonthDay.dayOfWeek.value
        var previousMonthDay = firstMonthDay.minusDays((dayOfWeek - 1).toLong())

        for (i in 1..35) {
            val dayNumber = previousMonthDay.dayOfMonth
            val dayInMilli =
                previousMonthDay.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
            daysOfMonth.add(
                Day(
                    dayInMilli = dayInMilli,
                    dayNumber = dayNumber,
                    isActive = dayOfWeek <= i && (monthLength + dayOfWeek) > i
                )
            )
            previousMonthDay = previousMonthDay.plusDays(1)
        }

        return daysOfMonth
    }
}