package net.azarquiel.chistesretrofitjpc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.azarquiel.chistesretrofitjpc.viewmodel.MainViewModel
import net.azarquiel.chistesretrofitjpc.screens.ChistesScreen
import net.azarquiel.chistesretrofitjpc.screens.DetailScreen
import net.azarquiel.chistesretrofitjpc.screens.MainScreen


@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = AppScreens.MainScreen.route){
        composable(AppScreens.MainScreen.route){
            MainScreen(navController, viewModel)
        }
        composable(AppScreens.ChistesScreen.route){
            ChistesScreen(navController, viewModel)
        }
        composable(AppScreens.DetailScreen.route) {
            DetailScreen(navController, viewModel)
        }
    }
}
sealed class AppScreens(val route: String) {
    object MainScreen: AppScreens(route = "MainScreen")
    object ChistesScreen: AppScreens(route = "ChistesScreen")
    object DetailScreen: AppScreens(route = "DetailScreen")
}
