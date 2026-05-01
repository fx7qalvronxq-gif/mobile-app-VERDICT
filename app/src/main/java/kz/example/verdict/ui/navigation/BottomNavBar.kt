package kz.example.verdict.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kz.example.verdict.ui.theme.*

@Composable
fun BottomNavBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Background,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Главная", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Gold,
                selectedTextColor = Gold,
                unselectedIconColor = DimText,
                unselectedTextColor = DimText,
                indicatorColor = SurfaceVariant
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Explore.route,
            onClick = { navController.navigate(Screen.Explore.route) },
            icon = { Icon(Icons.Default.Search, contentDescription = null) },
            label = { Text("Обзор", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Gold,
                selectedTextColor = Gold,
                unselectedIconColor = DimText,
                unselectedTextColor = DimText,
                indicatorColor = SurfaceVariant
            )
        )
        // FAB кнопка по центру
        NavigationBarItem(
            selected = currentRoute == Screen.Add.route,
            onClick = { navController.navigate(Screen.Add.route) },
            icon = {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(bottom = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = { navController.navigate(Screen.Add.route) },
                        containerColor = Gold,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = Background,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            },
            label = {},
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Background
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Saved.route,
            onClick = { navController.navigate(Screen.Saved.route) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("Избранное", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Gold,
                selectedTextColor = Gold,
                unselectedIconColor = DimText,
                unselectedTextColor = DimText,
                indicatorColor = SurfaceVariant
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = { navController.navigate(Screen.Profile.route) },
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Профиль", style = MaterialTheme.typography.labelSmall) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Gold,
                selectedTextColor = Gold,
                unselectedIconColor = DimText,
                unselectedTextColor = DimText,
                indicatorColor = SurfaceVariant
            )
        )
    }
}
