package com.example.phonecinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.phonecinemaapp.ui.login.LoginScreen
import com.example.phonecinemaapp.ui.registro.RegistroScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(
                onLoginExitoso = { /* Navegar a Home */ },
                onNavigateToRegistro = {
                    navController.navigate(AppScreens.RegistroScreen.route)
                }
            )
        }
        composable(route = AppScreens.RegistroScreen.route) {
            RegistroScreen(
                onNavigateToLogin = {
                    navController.navigate(AppScreens.LoginScreen.route)
                }
            )
        }
    }
}

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegistroScreen : AppScreens("registro_screen")
    // Aquí añadiremos más pantallas como Home, etc.
}