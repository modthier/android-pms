package com.pharmacy.pms.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pharmacy.pms.ui.screen.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Drugs : Screen("drugs")
    object Stock : Screen("stock")
    object Orders : Screen("orders")
    object Profile : Screen("profile")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        composable(Screen.Drugs.route) {
            DrugsScreen()
        }
        composable(Screen.Stock.route) {
            StockScreen()
        }
        composable(Screen.Orders.route) {
            OrdersScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
    }
}
