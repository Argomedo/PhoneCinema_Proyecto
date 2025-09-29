package com.example.phonecinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.phonecinemaapp.ui.home.HomeScreen
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
                    // Lógica para navegar a la pantalla de detalles de una película.
                    // Por ahora la dejamos vacía. En el futuro aquí navegarías a
                    // una nueva pantalla pasándole la 'movieId'.
                }
            )
        }
    }
}

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegistroScreen : AppScreens("registro_screen")
    object HomeScreen : AppScreens("home_screen")
    // Aquí añadiremos más pantallas como Home, etc.
}