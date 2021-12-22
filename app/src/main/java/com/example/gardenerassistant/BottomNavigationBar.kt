package com.example.gardenerassistant


import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gardenerassistant.ui.theme.Green
import com.example.gardenerassistant.utils.NavItem

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    items: List<NavItem>,
    OnItemClick: (String) -> Unit
) {
    val currentBackStack = navController.currentBackStackEntryAsState()
    val backStackRoute = currentBackStack.value?.destination?.route
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackRoute
            BottomNavigationItem(
                selected = selected,
                onClick = { if(!selected) OnItemClick(item.route) },
                icon = {
                    NavigationItem(isSelected = selected, navItem = item)
                }
            )
        }
    }
}

@Composable
fun NavigationItem(isSelected: Boolean, navItem: NavItem) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = navItem.icon,
            contentDescription = "icon",
            tint = if (isSelected) Green else Color.Gray
        )
        Text(
            text = navItem.title,
            color = if (isSelected) Green else Color.Gray,
            style = MaterialTheme.typography.body2
        )
    }
}