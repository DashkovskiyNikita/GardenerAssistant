@file:Suppress("NAME_SHADOWING")

package com.example.gardenerassistant.presentation.screens

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.gardenerassistant.R
import com.example.gardenerassistant.presentation.models.intents.UserPlantScreenIntent
import com.example.gardenerassistant.presentation.viewmodels.UserPlantViewModel
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun UserPlantScreen(
    modifier: Modifier = Modifier,
    plantId: Int = -1,
    navController: NavController,
    viewModel: UserPlantViewModel = getViewModel()
) {
    val state = viewModel.viewModelState.collectAsState()
    val plant = state.value.userPlantModel
    val context = LocalContext.current
    val isChange = plantId != -1

    LaunchedEffect(Unit) {
        if (isChange) viewModel.fetchUserPlant(id = plantId, context = context)
    }

    val imagePicker = imagePicker(callback = viewModel::saveImage)
    val boardingTimePicker = datePicker { time ->
        viewModel.onEvent(UserPlantScreenIntent.BoardingTimeChanged(time))
    }
    val collectionTimePicker = datePicker { time ->
        viewModel.onEvent(UserPlantScreenIntent.CollectionTimeChanged(time))
    }
    Scaffold(
        floatingActionButton = {
            SavePlantFloatingButton(modifier = modifier) {
                viewModel.insertUserPlant(isChange)
                if (isChange) {
                    showToast("Изменения сохранены", context)
                }else{
                    showToast("Растение добавлено", context)
                    navController.popBackStack()
                }
            }
        }
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(White)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            BackButton(navController = navController)
            ScreenTitleSection(isChange = isChange)
            Spacer(modifier = Modifier.height(5.dp))
            PlantNameField(
                value = plant.name,
                onValueChanged = viewModel::onEvent,
                suggestions = state.value.plantsSuggestions
            )
            Spacer(modifier = Modifier.height(5.dp))
            PlantSortField(
                value = plant.sort,
                onValueChanged = viewModel::onEvent,
                suggestions = state.value.sortsSuggestions
            )
            Spacer(modifier = Modifier.height(5.dp))
            BoardingTimeField(
                value = plant.boardingTime,
                onButtonClick = { boardingTimePicker.show() }
            )
            Spacer(modifier = Modifier.height(5.dp))
            CollectionTimeField(
                value = plant.collectionTime,
                onButtonClick = { collectionTimePicker.show() }
            )
            Spacer(modifier = Modifier.height(5.dp))
            NoteField(
                value = plant.note,
                onValueChanged = viewModel::onEvent
            )
            Spacer(modifier = Modifier.height(5.dp))
            AddImageButton { imagePicker.launch("image/*") }
            Spacer(modifier = Modifier.height(5.dp))
            if (state.value.bitmapLoading) CircleIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            state.value.bitmap?.let {
                UserPlantImage(bitmap = it) { viewModel.deleteImage(context) }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun AddImageButton(onClick: () -> Unit = {}) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Green),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Text(
            text = "Добавить фото",
            color = White,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BackButton(navController: NavController) {
    IconButton(
        onClick = { navController.popBackStack() }
    ) {
        Icon(
            modifier = Modifier.size(35.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Close icon",
            tint = Green,
        )
    }
}

@Composable
fun ScreenTitleSection(isChange: Boolean = true) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = if (isChange) "Растение" else "Новое растение",
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SavePlantFloatingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        backgroundColor = Green,
        contentColor = White
    ) {
        Icon(painter = painterResource(R.drawable.ic_save), contentDescription = null)
    }
}

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    label: String = "",
    placeHolder: String = "",
    value: String = "",
    icon: Painter? = null,
    onValueChanged: (String) -> Unit = {},
    onButtonClick: () -> Unit = {}
) {
    OutlinedTextField(
        readOnly = readOnly,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Green,
            backgroundColor = White,
            focusedIndicatorColor = Green,
            focusedLabelColor = Green
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label) },
        placeholder = { Text(text = placeHolder) },
        trailingIcon = {
            if (icon != null) IconButton(onClick = onButtonClick) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = icon,
                    contentDescription = "Edit icon"
                )
            }
        }
    )
}

@Composable
fun PlantNameField(
    value: String,
    suggestions: List<String> = listOf("1", "2", "3", "4"),
    onValueChanged: (UserPlantScreenIntent) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        R.drawable.expand_less_icon
    else
        R.drawable.expand_more_icon
    Column {
        BaseTextField(
            modifier = Modifier.onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
            label = "Имя растения",
            placeHolder = "Введите название растения",
            value = value,
            icon = painterResource(icon),
            onValueChanged = { onValueChanged(UserPlantScreenIntent.NameChanged(it)) },
            onButtonClick = { expanded = !expanded }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .sizeIn(maxHeight = 400.dp)
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onValueChanged(UserPlantScreenIntent.NameChanged(label))
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun PlantSortField(
    value: String,
    suggestions: List<String> = listOf("1", "2", "3", "4"),
    onValueChanged: (UserPlantScreenIntent) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        R.drawable.expand_less_icon
    else
        R.drawable.expand_more_icon
    Column {
        BaseTextField(
            modifier = Modifier.onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            },
            label = "Сорт растения",
            placeHolder = "Введите сорт растения",
            value = value,
            icon = painterResource(icon),
            onValueChanged = { onValueChanged(UserPlantScreenIntent.SortChanged(it)) },
            onButtonClick = { expanded = !expanded }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .sizeIn(maxHeight = 400.dp)
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onValueChanged(UserPlantScreenIntent.SortChanged(label))
                    }
                ) {
                    Text(text = label)
                }
            }
        }
    }
}

@Composable
fun BoardingTimeField(value: String, onButtonClick: () -> Unit = {}) {
    BaseTextField(
        label = "Время посадки",
        readOnly = true,
        icon = painterResource(R.drawable.calendar_icon),
        value = value,
        onButtonClick = onButtonClick
    )
}

@Composable
fun CollectionTimeField(value: String, onButtonClick: () -> Unit = {}) {
    BaseTextField(
        label = "Время сбора урожая",
        readOnly = true,
        icon = painterResource(R.drawable.calendar_icon),
        value = value,
        onButtonClick = onButtonClick
    )
}

@Composable
fun UserPlantImage(bitmap: Bitmap, onDeletedClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null
        )
        IconButton(
            modifier = Modifier
                .padding(top = 5.dp, end = 5.dp)
                .align(Alignment.TopEnd),
            onClick = onDeletedClick
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null
            )
        }
    }
}

@Composable
fun NoteField(value: String, onValueChanged: (UserPlantScreenIntent) -> Unit = {}) {
    BaseTextField(
        label = "Заметка",
        placeHolder = "Введите текст",
        value = value,
        onValueChanged = { onValueChanged(UserPlantScreenIntent.NoteChanged(it)) }
    )
}

fun showToast(message: String,context : Context) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()




