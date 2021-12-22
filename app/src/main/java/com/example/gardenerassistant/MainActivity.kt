package com.example.gardenerassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.gardenerassistant.utils.NavItem
import com.example.gardenerassistant.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            items = listOf(
                                NavItem(
                                    title = "Грядки",
                                    route = Screen.HomeScreen.route,
                                    icon = Icons.Default.Home
                                ),
                                NavItem(
                                    title = "Календарь",
                                    route = Screen.CalendarScreen.route,
                                    icon = Icons.Default.List
                                ),
                                NavItem(
                                    title = "Настройки",
                                    route = Screen.SettingsScreen.route,
                                    icon = Icons.Default.Settings
                                )
                            ),
                            OnItemClick = { route ->
                                navController.navigate(route)
                            }
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
