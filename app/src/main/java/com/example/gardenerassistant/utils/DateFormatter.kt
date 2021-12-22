package com.example.gardenerassistant.utils

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(millis: Long, pattern: String = "dd.MM.yyyy"): String {
        val date = Date(millis)
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(date)
    }

    fun getDateByData(year: Int, month: Int, day: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.timeInMillis
    }
}