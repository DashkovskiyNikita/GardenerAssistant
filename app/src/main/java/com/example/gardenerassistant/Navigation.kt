package com.example.gardenerassistant

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gardenerassistant.screens.*
import com.example.gardenerassistant.utils.Screen
import com.example.gardenerassistant.viewmodel.PlantViewModel
import com.example.gardenerassistant.viewmodel.CalendarViewModel
import com.example.gardenerassistant.viewmodel.HomeScreenViewModel


@SuppressLint("NewApi")
@ExperimentalMaterialApi
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(
                modifier = modifier,
                homeScreenViewModel = homeScreenViewModel,
                navController = navController
            )
        }
        composable(route = Screen.CalendarScreen.route) {
            val calendarViewModel = hiltViewModel<CalendarViewModel>()
            CalendarScreen(modifier = modifier,calendarViewModel)
        }
        composable(route = Screen.SettingsScreen.route) {
            SettingsScreen()
        }
        composable(
            route = Screen.PlantScreen.route + "/{isChanged}" + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("isChanged"){
                    type = NavType.BoolType
                    defaultValue = false
                    nullable = false
                }
            )
        ) { entry ->
            val plantViewModel = hiltViewModel<PlantViewModel>()
            PlantScreen(
                modifier = modifier,
                navController = navController,
                id = entry.arguments?.getInt("id")!!,
                plantViewModel = plantViewModel,
                isChange = entry.arguments?.getBoolean("isChanged")!!
            )
        }
    }
}