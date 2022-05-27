package com.example.gardenerassistant.presentation.screens


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gardenerassistant.R
import com.example.gardenerassistant.domain.models.UserPlantModel
import com.example.gardenerassistant.ui.theme.Green
import com.example.gardenerassistant.presentation.viewmodels.HomeScreenViewModel
import org.koin.androidx.compose.getViewModel

@ExperimentalMaterialApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    viewModel: HomeScreenViewModel = getViewModel()
) {
    val state = viewModel.viewModelState.collectAsState()
    Scaffold(
        floatingActionButton = {
            AddPlantFloatingButton(modifier = modifier) {
                navController.navigate(PlantScreen.UserPlant.route)
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.value.data) { plant ->
                    PlantCard(
                        userPlant = plant,
                        navController = navController,
                        onDeletedClick = viewModel::deletePlant
                    )
                }
            }
            when {
                state.value.isLoading -> CircleIndicator(modifier = Modifier.align(Alignment.Center))
                state.value.data.isEmpty() -> EmptyPlantListScreen(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun PlantCard(
    userPlant: UserPlantModel = UserPlantModel(
        name = "Картофель",
        boardingTime = "2020-02-01",
        collectionTime = "2020-03-01"
    ),
    navController: NavController = rememberNavController(),
    onDeletedClick: (UserPlantModel) -> Unit = {}
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(start = 15.dp, top = 15.dp, end = 15.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(1.dp, Color.Black, RoundedCornerShape(15.dp)),
        onClick = { navController.navigate(PlantScreen.UpdatePlant(userPlant.id).route) }
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CardText(value = userPlant.name, style = MaterialTheme.typography.h4)
                DeleteCardIcon { onDeletedClick(userPlant) }
            }
            CardTextSection(userPlant = userPlant)
            Spacer(modifier = Modifier.height(5.dp))
            CardImage()
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun Test() {
    PlantCard()
}

//Box(modifier = Modifier.fillMaxSize()) {
//
//            DeleteCardIcon(
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//                    .background(Color.Black),
//                onDeletedClick = { onDeletedClick(userPlant) }
//            )
//            CardTextSection(userPlant = userPlant, modifier = Modifier.align(Alignment.TopCenter))
//            Spacer(modifier = Modifier.height(5.dp))
//            DeleteCardImage()
//        }

@Composable
fun CardTextSection(modifier: Modifier = Modifier, userPlant: UserPlantModel) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        CardText(
            value = "Дата посадки : ${userPlant.boardingTime}",
            style = MaterialTheme.typography.body2
        )
        CardText(
            value = "Дата сбора : ${userPlant.collectionTime}",
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun CardImage(
    @DrawableRes drawable: Int = R.drawable.plant_image_releise
) {
    Image(
        painter = painterResource(drawable),
        contentDescription = "image",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    )
}

@Composable
fun CardText(
    value: String = "",
    style: TextStyle = MaterialTheme.typography.h1
) {
    Text(
        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
        text = value,
        color = Color.Black,
        style = style
    )
}

@Composable
fun DeleteCardIcon(onDeletedClick: () -> Unit = {}) {
    IconButton(
        modifier = Modifier
            .size(30.dp)
            .padding(end = 10.dp)
            .background(Color.White),
        onClick = onDeletedClick
    ) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            tint = Color.Black,
            contentDescription = "Delete item icon"
        )
    }
}

@Composable
fun CircleIndicator(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier,
        color = Green
    )
}


@Composable
fun AddPlantFloatingButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
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


