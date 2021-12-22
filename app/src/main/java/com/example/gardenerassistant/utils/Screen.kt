package com.example.gardenerassistant.utils

sealed class Screen(val route: String) {
    object HomeScreen : Screen("homeScreen")
    object CalendarScreen : Screen("calendarScreen")
    object SettingsScreen : Screen("settingsScreen")
    object PlantScreen : Screen("plantScreen"){
        fun withArg(id : Int = 0,isChanged : Boolean = false) : String{
            return buildString {
                append(route)
                append("/$isChanged")
                append("/$id")
            }
        }
    }
}
