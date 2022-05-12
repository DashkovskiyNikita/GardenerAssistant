package com.example.gardenerassistant.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gardenerassistant.R
import com.example.gardenerassistant.ui.theme.Green
import com.example.gardenerassistant.utils.DateHelper
import com.example.gardenerassistant.utils.Day
import com.example.gardenerassistant.utils.Plant
import com.example.gardenerassistant.viewmodel.CalendarViewModel
import java.util.*


@Composable
fun CalendarScreen(modifier: Modifier, calendarViewModel: CalendarViewModel) {

    val monthDays by calendarViewModel.monthDays.observeAsState()
    val screenState by calendarViewModel.screenState.observeAsState()
    val year = calendarViewModel.year.value
    val month = calendarViewModel.month.value


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CalendarView(
            monthDays = monthDays!!,
            collectionDays = screenState?.data!!,
            year = year!!,
            month = DateHelper.getMonthNameByNumber(month!!),
            onNextMonthClicked = calendarViewModel::onNextMonthClicked,
            onPreviousMonthClicked = calendarViewModel::onPreviousMonthClicked
        )
        Spacer(modifier = Modifier.size(5.dp))
        Divider()
        Spacer(modifier = Modifier.size(5.dp))
        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(screenState!!.data.size) {
                    val data = screenState!!.data
                    PlantCard(data = data[it])
                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
            when {
                screenState!!.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Green
                )
                screenState!!.data.isEmpty() -> EmptyListInfo(modifier = modifier)
            }
        }
    }
}

@Composable
fun CalendarView(
    dayOfWeekNames: List<String> = listOf("ПН", "ВТ", "СР", "ЧТ", "ПТ", "СБ", "ВС"),
    monthDays: List<Day>,
    collectionDays: List<Plant>,
    year: Int = 2021,
    month: String? = "Январь",
    onNextMonthClicked: () -> Unit,
    onPreviousMonthClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onPreviousMonthClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigate_before),
                    contentDescription = null,
                    tint = Green,
                    modifier = Modifier.size(35.dp)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "$year", textAlign = TextAlign.Center)
                Text(text = "$month", textAlign = TextAlign.Center)
            }
            IconButton(onClick = { onNextMonthClicked() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_navigate_next),
                    contentDescription = null,
                    tint = Green,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dayOfWeekNames.forEach { day ->
                Text(text = day)
            }
        }
        ShowDays(days = monthDays, collectionDays)
    }
}

@Composable
fun ShowDays(days: List<Day>, busyDays: List<Plant>) {
    var j = 0
    for (i in 1..days.size / 7) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (k in 1..7) {
                val value = days[j++]
                var isBusy = false
                busyDays.forEach {
                    val day = DateHelper.getDay(it.collectionTime)
                    if (day == value.dayNumber && value.isActive) {
                        isBusy = true
                        return@forEach
                    }
                }
                if (isBusy) BusyDay(value = value) else EmptyDay(value = value)
            }
        }
    }
}

@Composable
fun PlantCard(data: Plant) {
    val day = DateHelper.getDay(data.collectionTime)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Green)
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$day", color = Color.White)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Сбор урожая у (${data.plantTitle})")
    }
}

@Composable
fun EmptyListInfo(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_grass),
                contentDescription = null,
                tint = Green,
                modifier = Modifier.size(60.dp)
            )
            Text(
                "В этом месяце нет сбора урожая",
                textAlign = TextAlign.Center,
                modifier = Modifier.width(300.dp)
            )
        }
    }
}

@Composable
fun EmptyDay(value: Day) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${value.dayNumber}",
            color = if (value.isActive) Color.Black else Color.Gray
        )
    }
}

@Composable
fun BusyDay(value: Day) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(30.dp)
            .clip(CircleShape)
            .background(Green),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${value.dayNumber}",
            color = Color.White
        )
    }
}
