package com.example.gardenerassistant

import com.example.gardenerassistant.utils.DateHelper
import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun dateHelper_Test(){
        val days = DateHelper.getMonthDaysByDate().map { it.dayNumber }
        println("ПН ВТ СР ЧТ ПТ СБ ВС")
        days.forEachIndexed{index,value ->
            if(index % 7 == 0 && index != 0) print("\n")
            if(value < 10) print(" $value ") else print("$value ")
        }
    }
}