package com.example.phonecinemaapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.phonecinemaapp.data.session.UserSession
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
import com.example.phonecinemaapp.ui.reseñas.ReviewViewModelFactory
import com.example.phonecinemaapp.ui.roles.AdminScreen
import com.example.phonecinemaapp.ui.roles.ModeradorScreen
import com.example.phonecinemaapp.ui.roles.ManageReviewsScreen
import com.example.phonecinemaapp.ui.roles.ManageUsersScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext

    val database = AppDatabase.getInstance(context)
    val userRepository = UserRepository(database.userDao())
    val reviewRepository = ReviewRepository(database.reviewDao())

    // Crear Admin y Moderador si no existen
    LaunchedEffect(Unit) {
        val admin = userRepository.getUserByEmail("admin@phonecinema.com")
        val mod = userRepository.getUserByEmail("mod@phonecinema.com")

        if (admin == null) {
            userRepository.insertUser(
                name = "Administrador",
                email = "admin@phonecinema.com",
                password = "Admin123!",
                role = "Admin"
            )
        }
        if (mod == null) {
            userRepository.insertUser(
                name = "Moderador",
                email = "mod@phonecinema.com",
                password = "Mod123!",
                role = "Moderador"
            )
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {

        // --- Login ---
        composable(AppScreens.LoginScreen.route) {
            val factory = LoginViewModelFactory(userRepository)
            val loginViewModel: LoginViewModel = viewModel(factory = factory)
            LoginScreen(
                loginViewModel = loginViewModel,
                onLoginExitoso = {
                    val user = UserSession.currentUser
                    when (user?.role) {
                        "Admin" -> navController.navigate(AppScreens.AdminScreen.route)
                        "Moderador" -> navController.navigate(AppScreens.ModeradorScreen.route)
                        else -> navController.navigate(AppScreens.HomeScreen.route)
                    }
                },
                onNavigateToRegistro = { navController.navigate(AppScreens.RegistroScreen.route) }
            )
        }

        // --- Registro ---
        composable(AppScreens.RegistroScreen.route) {
            val factory = RegistroViewModelFactory(userRepository)
            val registroViewModel: RegistroViewModel = viewModel(factory = factory)
            RegistroScreen(
                registroViewModel = registroViewModel,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // --- Usuario (Home) ---
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
                onNavigateToProfile = { navController.navigate(AppScreens.PerfilScreen.route) }
            )
        }

        // --- Admin ---
        composable(AppScreens.AdminScreen.route) {
            AdminScreen(
                onNavigateToUsers = { navController.navigate(AppScreens.ManageUsersScreen.route) },
                onNavigateToReviews = { navController.navigate(AppScreens.ManageReviewsScreen.route) },
                onLogout = {
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                }
            )
        }

        // --- Moderador ---
        composable(AppScreens.ModeradorScreen.route) {
            ModeradorScreen(
                onNavigateToReviews = { navController.navigate(AppScreens.ManageReviewsScreen.route) },
                onLogout = {
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                }
            )
        }

        // --- Gestionar Usuarios ---
        composable(AppScreens.ManageUsersScreen.route) {
            ManageUsersScreen(
                userRepo = userRepository,
                onBack = { navController.popBackStack() }
            )
        }

        // --- Gestionar Reseñas ---
        composable(AppScreens.ManageReviewsScreen.route) {
            ManageReviewsScreen(
                reviewRepo = reviewRepository,
                onBack = { navController.popBackStack() }
            )
        }

        // --- Reseñas de Películas ---
        composable(
            route = AppScreens.ReviewScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
            val factory = ReviewViewModelFactory(reviewRepository)
            val reviewViewModel: ReviewViewModel = viewModel(factory = factory)

            ReviewScreen(
                movieId = movieId,
                onBackClick = { navController.popBackStack() },
                onNavigateToProfile = { navController.navigate(AppScreens.PerfilScreen.route) }
            )
        }

        // --- Perfil ---
        composable(AppScreens.PerfilScreen.route) {
            val userEmail = UserSession.currentUser?.email ?: ""
            PerfilScreen(
                userEmail = userEmail,
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
    object AdminScreen : AppScreens("admin_screen")
    object ModeradorScreen : AppScreens("moderador_screen")
    object ManageUsersScreen : AppScreens("manage_users")
    object ManageReviewsScreen : AppScreens("manage_reviews")
}
