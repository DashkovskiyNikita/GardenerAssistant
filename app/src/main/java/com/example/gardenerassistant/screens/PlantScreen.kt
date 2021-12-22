@file:Suppress("NAME_SHADOWING")

package com.example.gardenerassistant.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gardenerassistant.R
import com.example.gardenerassistant.ui.theme.Green
import com.example.gardenerassistant.utils.DateFormatter
import com.example.gardenerassistant.utils.Screen
import com.example.gardenerassistant.viewmodel.PlantViewModel
import java.util.*

@SuppressLint("ShowToast")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PlantScreen(
    modifier: Modifier,
    navController: NavController,
    id : Int,
    plantViewModel: PlantViewModel,
    isChange : Boolean = false
) {

    if(isChange) plantViewModel.fetchPlantById(id)

    val plantName = plantViewModel.plantName.value
    val boardingTime = plantViewModel.boardingTime.value
    val collectionTime = plantViewModel.collectionTime.value
    val noteValue = plantViewModel.noteValue.value

    val context = LocalContext.current

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            modifier = modifier,
            onClick = {
                if(isChange) plantViewModel.updatePlant() else plantViewModel.addNewPlant()
                val message = if (isChange) "Изменения внесены" else "Растение добавлено"
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                if(!isChange) navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.PlantScreen.route + "/$isChange" + "/$id") {
                        inclusive = true
                    }
                }
            },
            backgroundColor = Green,
            contentColor = Color.White
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_save), contentDescription = null)
        }
    }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp)
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Close icon",
                    tint = Green,

                    )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = if(isChange) "Растение" else "Новое растение",
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Green,
                    focusedIndicatorColor = Green,
                    focusedLabelColor = Green
                ),
                value = plantName,
                onValueChange = plantViewModel::onPlantNameChanged,
                label = { Text("Имя растения") },
                placeholder = { Text("Введите название растения") }
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Green,
                    focusedIndicatorColor = Green,
                    focusedLabelColor = Green
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                value = DateFormatter.getFormattedDate(boardingTime),
                onValueChange = { },
                label = { Text("Дата посадки") },
                trailingIcon = {
                    IconButton(onClick = {
                        showDatePickerDialog(context, plantViewModel::onBoardingTimeChanged)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit icon"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
            OutlinedTextField(
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Green,
                    focusedIndicatorColor = Green,
                    focusedLabelColor = Green
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                value = if (collectionTime == null) "" else DateFormatter.getFormattedDate(
                    collectionTime
                ),
                onValueChange = { },
                label = { Text("Дата сбора урожая") },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDatePickerDialog(
                                context,
                                plantViewModel::onCollectionTimeChanged
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit icon"
                        )
                    }
                }
            )
            OutlinedTextField(
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = Green,
                    focusedIndicatorColor = Green,
                    focusedLabelColor = Green
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                value = noteValue,
                onValueChange = plantViewModel::onNoteValueChanged,
                label = { Text("Заметка") },
                placeholder = { Text("Введите текст ") }
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.N)
private fun showDatePickerDialog(context: Context, block: (Long) -> Unit) {

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            block(DateFormatter.getDateByData(year, month, dayOfMonth))
        },
        year,
        month,
        day
    ).show()
}

