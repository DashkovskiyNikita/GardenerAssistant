package com.example.gardenerassistant

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.example.gardenerassistant.presentation.utils.getMonthDays
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var context : Context

    @Before
    fun provideContext(){
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun monthDaysTest() {
        val date = LocalDate.of(2022, 11, 6)
        val result = getMonthDays(date)
        println(result)
    }
}