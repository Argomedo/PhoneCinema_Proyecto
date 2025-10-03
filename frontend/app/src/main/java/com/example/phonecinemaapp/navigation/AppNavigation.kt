package com.example.phonecinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.phonecinemaapp.ui.home.HomeScreen
import com.example.phonecinemaapp.ui.login.LoginScreen
import com.example.phonecinemaapp.ui.registro.RegistroScreen
import com.example.phonecinemaapp.ui.reseñas.ReviewScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(route = AppScreens.LoginScreen.route) {
            LoginScreen(
                onLoginExitoso = {
                    // --- CAMBIO IMPORTANTE AQUÍ ---
                    // Al ser exitoso el login, navegamos al Home.
                    navController.navigate(AppScreens.HomeScreen.route) {
                        // Esto es para limpiar el stack de navegación.
                        // Evita que el usuario pueda volver a la pantalla de Login
                        // al presionar el botón de "atrás" después de iniciar sesión.
                        popUpTo(AppScreens.LoginScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate(AppScreens.RegistroScreen.route)
                }
            )
        }

        composable(route = AppScreens.RegistroScreen.route) {
            RegistroScreen(
                onNavigateToLogin = {
                    // Si el usuario ya tiene cuenta, puede volver al login
                    navController.navigateUp() // O navigate(AppScreens.LoginScreen.route)
                }
            )
        }

        // --- NUEVO DESTINO AÑADIDO ---
        // Aquí definimos qué se debe mostrar para la ruta del HomeScreen.
        composable(route = AppScreens.HomeScreen.route) {
            HomeScreen(
                onLogout = {
                    // Cuando el usuario cierre sesión desde el Home...
                    navController.navigate(AppScreens.LoginScreen.route) {
                        // Limpiamos todo el stack anterior para que no pueda volver
                        // al Home con el botón de "atrás".
                        popUpTo(AppScreens.HomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToMovieDetails = { movieId ->
                    navController.navigate("${AppScreens.ReviewScreen.route}/$movieId")
                }
            )
        }

        composable(
            route = "${AppScreens.ReviewScreen.route}/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1

            ReviewScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegistroScreen : AppScreens("registro_screen")
    object HomeScreen : AppScreens("home_screen")
    object ReviewScreen : AppScreens("review_screen")
    // Aquí añadiremos más pantallas como Home, etc.
}