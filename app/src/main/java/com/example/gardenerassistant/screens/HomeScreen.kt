package com.example.gardenerassistant.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gardenerassistant.R
import com.example.gardenerassistant.ui.theme.Green
import com.example.gardenerassistant.utils.DateFormatter
import com.example.gardenerassistant.utils.Plant
import com.example.gardenerassistant.utils.Screen
import com.example.gardenerassistant.viewmodel.HomeScreenViewModel

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel,
    navController: NavController
) {
    val state = homeScreenViewModel.dataState.value

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            modifier = modifier,
            onClick = { navController.navigate(Screen.PlantScreen.withArg()) },
            backgroundColor = Green,
            contentColor = Color.White,
        ) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = Icons.Filled.Add,
                contentDescription = "floating button icon"
            )
        }
    }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.data.size) {
                    PlantCard(plant = state.data[it], navController)
                }
            }

            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Green
                    )
                }
                state.data.isEmpty() -> EmptyPlantListScreen(modifier = modifier)
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun PlantCard(plant: Plant, navController: NavController) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(start = 15.dp, top = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
        onClick = {
            navController.navigate(
                Screen.PlantScreen.withArg(
                    id = plant.id!!,
                    isChanged = true
                )
            )
        }
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                text = plant.plantTitle,
                color = Color.Black,
                style = MaterialTheme.typography.h5
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                text = "Дата посадки : ${DateFormatter.getFormattedDate(plant.boardingTime)}",
                color = Color.Black,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                text = "Дата сборки : ${DateFormatter.getFormattedDate(plant.collectionTime)}",
                color = Color.Black,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(5.dp))
            Image(
                painter = painterResource(id = R.drawable.plant_image_releise),
                contentDescription = "image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth().height(150.dp)
            )
        }

    }
}


