package kz.example.verdict.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.example.verdict.ui.screens.home.HomeScreen
import kz.example.verdict.ui.screens.explore.ExploreScreen
import kz.example.verdict.ui.screens.add.AddVerdictScreen
import kz.example.verdict.ui.screens.saved.SavedScreen
import kz.example.verdict.ui.screens.profile.ProfileScreen
import kz.example.verdict.ui.screens.item.ItemDetailScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Add : Screen("add")
    object Saved : Screen("saved")
    object Profile : Screen("profile")
    object ItemDetail : Screen("item/{itemId}") {
        fun createRoute(itemId: Int) = "item/$itemId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController, context)
            }
            composable(Screen.Explore.route) {
                ExploreScreen(navController, context)
            }
            composable(Screen.Add.route) {
                AddVerdictScreen(navController, context)
            }
            composable(Screen.Saved.route) {
                SavedScreen(navController, context)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(context)
            }
            composable(Screen.ItemDetail.route) { backStack ->
                val itemId = backStack.arguments?.getString("itemId")?.toIntOrNull() ?: 0
                ItemDetailScreen(itemId, navController, context)
            }
        }
    }
}
