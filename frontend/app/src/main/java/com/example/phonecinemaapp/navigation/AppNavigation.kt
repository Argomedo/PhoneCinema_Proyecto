package com.example.phonecinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.phonecinemaapp.ui.home.HomeScreen
import com.example.phonecinemaapp.ui.login.LoginScreen
import com.example.phonecinemaapp.ui.perfil.PerfilScreen
import com.example.phonecinemaapp.ui.registro.RegistroScreen
import com.example.phonecinemaapp.ui.registro.RegistroViewModel
import com.example.phonecinemaapp.ui.reseñas.ReviewScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(
                onLoginExitoso = {
                    navController.navigate(AppScreens.HomeScreen.route) {
                        popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate(AppScreens.RegistroScreen.route)
                }
            )
        }

        composable(AppScreens.RegistroScreen.route) {
            val registroViewModel: RegistroViewModel = viewModel()
            RegistroScreen(
                registroViewModel = registroViewModel,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(AppScreens.HomeScreen.route) {
            HomeScreen(
                onLogout = {
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(AppScreens.HomeScreen.route) { inclusive = true }
                    }
                },
                onNavigateToMovieDetails = { movieId ->
                    navController.navigate("${AppScreens.ReviewScreen.route}/$movieId")
                },
                onNavigateToProfile = {
                    navController.navigate(AppScreens.PerfilScreen.route)
                }
            )
        }

        composable(
            route = "${AppScreens.ReviewScreen.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
            ReviewScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() },
                onNavigateToProfile = {
                    navController.navigate(AppScreens.PerfilScreen.route)
                }
            )
        }
        composable(AppScreens.PerfilScreen.route) {
            PerfilScreen(
                onBackClick = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(AppScreens.PerfilScreen.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegistroScreen : AppScreens("registro_screen")
    object HomeScreen : AppScreens("home_screen")
    object ReviewScreen : AppScreens("review_screen")
    object PerfilScreen : AppScreens ("perfil_screen")
}
