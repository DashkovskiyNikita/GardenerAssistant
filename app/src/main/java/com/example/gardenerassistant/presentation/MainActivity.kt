package com.example.gardenerassistant.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.gardenerassistant.presentation.screens.BottomNavigationBar
import com.example.gardenerassistant.presentation.screens.NavItem
import com.example.gardenerassistant.presentation.screens.Navigation
import com.example.gardenerassistant.presentation.screens.PlantScreen

private val navItems = listOf(
    NavItem(
        title = "Грядки",
        route = PlantScreen.Home.route,
        icon = Icons.Default.Home
    ),
    NavItem(
        title = "Календарь",
        route = PlantScreen.Calendar.route,
        icon = Icons.Default.List
    ),
    NavItem(
        title = "Настройки",
        route = PlantScreen.Settings.route,
        icon = Icons.Default.Settings
    )
)

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            items = navItems,
                            OnItemClick = navController::navigate
                        )
                    }
                ) { paddingValues ->
                    Navigation(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController
                    )
                }
            }
        }
    }
}
