package com.example.gardenerassistant.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gardenerassistant.R
import com.example.gardenerassistant.domain.models.CalendarPlantModel
import com.example.gardenerassistant.presentation.models.intents.CalendarScreenIntent
import com.example.gardenerassistant.presentation.utils.MonthDay
import com.example.gardenerassistant.presentation.viewmodels.ICalendarScreenViewModel
import com.example.gardenerassistant.ui.theme.Green
import org.koin.androidx.compose.getViewModel

val daysOfWeek = listOf(
    R.string.monday,
    R.string.tuesday,
    R.string.wednesday,
    R.string.thursday,
    R.string.friday,
    R.string.saturday,
    R.string.sunday
)

@Composable
fun CalendarScreen(
    modifier: Modifier,
    viewModel: ICalendarScreenViewModel = getViewModel()
) {
    val state = viewModel.viewModelState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CalendarView(
            monthDays = state.value.monthDays,
            collectionDays = state.value.plantsList,
            year = state.value.year ?: 2022,
            month = state.value.monthName?.let { stringResource(it) },
            onNext = viewModel::onEvent,
            onPrevious = viewModel::onEvent
        )
        Spacer(modifier = Modifier.size(5.dp))
        Divider()
        Spacer(modifier = Modifier.size(5.dp))
        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.value.plantsList) { plant ->
                    PlantCard(data = plant)
                    Spacer(modifier = Modifier.size(5.dp))
                }
            }
            when {
                state.value.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Green
                )
                state.value.plantsList.isEmpty() -> EmptyListInfo(modifier = modifier)
            }
        }
    }
}

@Composable
fun CalendarView(
    dayOfWeekNames: List<Int> = daysOfWeek,
    monthDays: List<MonthDay>,
    collectionDays: List<CalendarPlantModel>,
    year: Int = 2021,
    month: String? = "Январь",
    onNext: (CalendarScreenIntent) -> Unit,
    onPrevious: (CalendarScreenIntent) -> Unit
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
            IconButton(
                onClick = { onPrevious(CalendarScreenIntent.PreviousMonthClicked) }
            ) {
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
            IconButton(
                onClick = { onNext(CalendarScreenIntent.NextMonthClicked) }
            ) {
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
                Text(text = stringResource(day))
            }
        }
        ShowDays(days = monthDays, collectionDays)
    }
}

@Composable
fun ShowDays(days: List<MonthDay>, plantsList: List<CalendarPlantModel>) {
    var j = 0
    for (i in 1..days.size / 7) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val collectionDays = plantsList.map { it.monthDay }
            for (k in 1..7) {
                val day = days[j++]
                if (day.value in collectionDays && day.active) BusyDay(day) else EmptyDay(day)
            }
        }
    }
}

@Composable
fun PlantCard(data: CalendarPlantModel) {
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
            Text(text = "${data.monthDay}", color = Color.White)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Сбор урожая у (${data.name})")
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
fun EmptyDay(day: MonthDay) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${day.value}",
            color = if (day.active) Color.Black else Color.Gray
        )
    }
}

@Composable
fun BusyDay(day: MonthDay) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(30.dp)
            .clip(CircleShape)
            .background(Green),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${day.value}",
            color = Color.White
        )
    }
}
