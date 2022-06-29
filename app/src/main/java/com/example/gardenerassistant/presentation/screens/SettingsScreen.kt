package com.example.gardenerassistant.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gardenerassistant.R

@Composable
fun SettingsScreen(modifier : Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Divider()
        SettingsButton(
            icon = painterResource(R.drawable.database_icon),
            title = "Очистить данные"
        )
        Divider()
        SettingsButton(
            icon = painterResource(R.drawable.share_icon),
            title = "Связь с разработчиком"
        )
        Divider()
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = "version:0.0.1-alpha",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight(500)
        )
    }
}

@Composable
fun SettingsButton(
    icon: Painter,
    title: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = icon,
            tint = Color.Green,
            contentDescription = null
        )
        Divider(modifier = Modifier.width(5.dp), color = Color.Transparent)
        Text(
            text = title,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight(500)
        )
    }
}

@Preview
@Composable
fun SettingsPreview(){
    SettingsScreen()
}