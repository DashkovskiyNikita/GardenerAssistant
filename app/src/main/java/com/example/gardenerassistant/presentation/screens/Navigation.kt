package com.example.gardenerassistant.presentation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.gardenerassistant.presentation.screens.CalendarScreen
import com.example.gardenerassistant.presentation.screens.HomeScreen
import com.example.gardenerassistant.presentation.screens.UserPlantScreen
import com.example.gardenerassistant.presentation.screens.SettingsScreen

sealed class PlantScreen(val route: String) {
    object Home : PlantScreen("main")
    object Calendar : PlantScreen("calendar")
    object Settings : PlantScreen("settings")
    object UserPlant : PlantScreen("plant/-1")
    data class UpdatePlant(val id: Int) : PlantScreen("plant/$id")
}

@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = PlantScreen.Home.route
    ) {
        composable(route = PlantScreen.Home.route) {
            HomeScreen(
                modifier = modifier,
                navController = navController
            )
        }
        composable(route = PlantScreen.Calendar.route) {
            CalendarScreen(modifier = modifier)
        }
        composable(route = PlantScreen.Settings.route) {
            SettingsScreen()
        }
        composable(
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    nullable = false
                }
            ),
            route = "plant/{id}"
        ) { entry ->
            UserPlantScreen(
                modifier = modifier,
                plantId = entry.arguments?.getInt("id")!!,
                navController = navController
            )
        }
    }
}
//@SuppressLint("NewApi")
//@ExperimentalMaterialApi
//@Composable
//fun Navigation(
//    modifier: Modifier = Modifier,
//    navController: NavHostController
//) {
//    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
//        composable(route = Screen.HomeScreen.route) {
//            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
//            HomeScreen(
//                modifier = modifier,
//                homeScreenViewModel = homeScreenViewModel,
//                navController = navController
//            )
//        }
//        composable(route = Screen.CalendarScreen.route) {
//            val calendarViewModel = hiltViewModel<CalendarViewModel>()
//            //CalendarScreen(modifier = modifier,calendarViewModel)
//        }
//        composable(route = Screen.SettingsScreen.route) {
//            SettingsScreen()
//        }
//        composable(
//            route = Screen.PlantScreen.route + "/{isChanged}" + "/{id}",
//            arguments = listOf(
//                navArgument("id") {
//                    type = NavType.IntType
//                    defaultValue = 0
//                    nullable = false
//                },
//                navArgument("isChanged"){
//                    type = NavType.BoolType
//                    defaultValue = false
//                    nullable = false
//                }
//            )
//        ) { entry ->
//            val plantViewModel = hiltViewModel<PlantViewModel>()
//            PlantScreen(
//                modifier = modifier,
//                navController = navController,
//                id = entry.arguments?.getInt("id")!!,
//                plantViewModel = plantViewModel,
//                isChange = entry.arguments?.getBoolean("isChanged")!!
//            )
//        }
//    }
//}