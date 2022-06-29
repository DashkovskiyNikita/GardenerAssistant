package com.example.gardenerassistant.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gardenerassistant.R
import com.example.gardenerassistant.ui.theme.Green

@Composable
fun EmptyPlantListScreen(modifier: Modifier = Modifier) {
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
            "Пока нет растений.Чтобы добавить растение нажмите на '+'",
            textAlign = TextAlign.Center,
            modifier = Modifier.width(300.dp)
        )
    }

}