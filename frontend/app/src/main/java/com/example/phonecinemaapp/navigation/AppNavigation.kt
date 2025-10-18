package com.example.phonecinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.phonecinemaapp.data.local.database.AppDatabase
import com.example.phonecinemaapp.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.ui.home.HomeScreen
import com.example.phonecinemaapp.ui.login.LoginScreen
import com.example.phonecinemaapp.ui.login.LoginViewModel
import com.example.phonecinemaapp.ui.login.LoginViewModelFactory
import com.example.phonecinemaapp.ui.perfil.PerfilScreen
import com.example.phonecinemaapp.ui.registro.RegistroScreen
import com.example.phonecinemaapp.ui.registro.RegistroViewModel
import com.example.phonecinemaapp.ui.registro.RegistroViewModelFactory
import com.example.phonecinemaapp.ui.reseñas.ReviewScreen
import com.example.phonecinemaapp.ui.reseñas.ReviewViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.phonecinemaapp.ui.reseñas.ReviewViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext

    val database = AppDatabase.getInstance(context)
    val userRepository = UserRepository(database.userDao())
    val reviewRepository = ReviewRepository(database.reviewDao())

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(AppScreens.LoginScreen.route) {
            val factory = LoginViewModelFactory(userRepository)
            val loginViewModel: LoginViewModel = viewModel(factory = factory)
            LoginScreen(
                loginViewModel = loginViewModel,
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
            val factory = RegistroViewModelFactory(userRepository)
            val registroViewModel: RegistroViewModel = viewModel(factory = factory)
            RegistroScreen(
                registroViewModel = registroViewModel,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(AppScreens.HomeScreen.route) {
            HomeScreen(
                onLogout = {
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                },
                onNavigateToMovieDetails = { movieId ->
                    navController.navigate(AppScreens.ReviewScreen.createRoute(movieId))
                },
                onNavigateToProfile = {
                    navController.navigate(AppScreens.PerfilScreen.route)
                }
            )
        }

        composable(
            route = AppScreens.ReviewScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val context = LocalContext.current
            val database = AppDatabase.getInstance(context)
            val reviewRepo = ReviewRepository(database.reviewDao())
            val factory = ReviewViewModelFactory(reviewRepo)
            val reviewViewModel: ReviewViewModel = viewModel(factory = factory)

            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
            ReviewScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() },
                onNavigateToProfile = { navController.navigate(AppScreens.PerfilScreen.route) },
            )
        }


        composable(AppScreens.PerfilScreen.route) {
            PerfilScreen(
                onBackClick = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
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
    object PerfilScreen : AppScreens("perfil_screen")
    object ReviewScreen : AppScreens("review_screen/{movieId}") {
        fun createRoute(movieId: Int) = "review_screen/$movieId"
    }
}
