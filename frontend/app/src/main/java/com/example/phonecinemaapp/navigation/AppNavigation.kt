package com.example.phonecinemaapp.navigation

import ReviewApi
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.phonecinema.data.remote.AuthApi
import com.example.phonecinema.data.remote.FeedbackApi
import com.example.phonecinema.data.remote.RemoteModule
import com.example.phonecinema.data.remote.UserApi
import com.example.phonecinema.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.repository.AuthRepository
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.data.session.UserSession
import com.example.phonecinemaapp.ui.home.HomeScreen
import com.example.phonecinemaapp.ui.login.LoginScreen
import com.example.phonecinemaapp.ui.login.LoginViewModel
import com.example.phonecinemaapp.ui.perfil.PerfilScreen
import com.example.phonecinemaapp.ui.perfil.PerfilViewModel
import com.example.phonecinemaapp.ui.registro.RegistroScreen
import com.example.phonecinemaapp.ui.registro.RegistroViewModel
import com.example.phonecinemaapp.ui.resenas.ReviewScreen
import com.example.phonecinemaapp.ui.resenas.ReviewViewModel
import com.example.phonecinemaapp.ui.resenas.ReviewViewModelFactory
import com.example.phonecinemaapp.ui.roles.AdminScreen
import com.example.phonecinemaapp.ui.roles.ManageReviewsScreen
import com.example.phonecinemaapp.ui.roles.ManageUsersScreen
import com.example.phonecinemaapp.ui.roles.ModeradorScreen
import com.example.phonecinemaapp.ui.feedback.FeedbackScreen  // Importación para la pantalla de Feedback
import com.example.phonecinemaapp.ui.feedback.FeedbackViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val authApi = RemoteModule.createUsuarios(AuthApi::class.java)
    val userApi = RemoteModule.createUsuarios(UserApi::class.java)
    val reviewApi = RemoteModule.createResenas(ReviewApi::class.java)
    val feedbackApi = RemoteModule.createFeedback(FeedbackApi::class.java) // API de Feedback

    val authRepository = AuthRepository()
    val userRepository = UserRepository(userApi)
    val reviewRepository = ReviewRepository(reviewApi)

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(AppScreens.LoginScreen.route) {
            val vm = LoginViewModel(authRepository, userRepository)
            LoginScreen(
                loginViewModel = vm,
                onLoginExitoso = {
                    val rol = UserSession.currentUser?.rol?.uppercase()
                    val destino = when (rol) {
                        "ADMIN" -> AppScreens.AdminScreen.route
                        "MODERADOR" -> AppScreens.ModeradorScreen.route
                        else -> AppScreens.HomeScreen.route
                    }
                    navController.navigate(destino) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate(AppScreens.RegistroScreen.route)
                }
            )
        }

        composable(AppScreens.RegistroScreen.route) {
            val vm = RegistroViewModel(userRepository)
            RegistroScreen(
                registroViewModel = vm,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable(AppScreens.HomeScreen.route) {
            HomeScreen(
                onLogout = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                },
                onNavigateToMovieDetails = { movieId -> navController.navigate(AppScreens.ReviewScreen.createRoute(movieId)) },
                onNavigateToProfile = { navController.navigate(AppScreens.PerfilScreen.route) },
                onNavigateToFeedback = { navController.navigate(AppScreens.FeedbackScreen.route) }  // Ruta hacia Feedback
            )
        }

        composable(
            AppScreens.ReviewScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
            val vm: ReviewViewModel = viewModel(factory = ReviewViewModelFactory(reviewRepository))
            ReviewScreen(
                navController = navController,
                movieId = movieId,
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                },
                reviewViewModel = vm
            )
        }

        composable(AppScreens.PerfilScreen.route) {
            val id = UserSession.currentUser?.id ?: return@composable run {
                navController.navigate(AppScreens.LoginScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                }
            }

            val vm = PerfilViewModel(userRepository, reviewRepository)
            PerfilScreen(
                navController = navController,
                userId = id,
                perfilViewModel = vm,
                onBackClick = { navController.popBackStack() },
                onLogout = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                },
                onFeedbackClick = { navController.navigate(AppScreens.FeedbackScreen.route) }
            )
        }

        // Ruta para la pantalla de Feedback
        composable(AppScreens.FeedbackScreen.route) {
            val feedbackViewModel: FeedbackViewModel = viewModel()  // Usamos viewModel() para crear el ViewModel
            FeedbackScreen(
                viewModel = feedbackViewModel,  // Pasamos el ViewModel a la pantalla
                navController = navController
            )
        }

        composable(AppScreens.AdminScreen.route) {
            AdminScreen(
                navController = navController,
                onNavigateToUsers = { navController.navigate(AppScreens.UsersManagementScreen.route) },
                onNavigateToReviews = { navController.navigate(AppScreens.ReviewsManagementScreen.route) },
                onLogout = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.ModeradorScreen.route) {
            ModeradorScreen(
                navController = navController,
                onNavigateToReviews = { navController.navigate(AppScreens.ReviewsManagementScreen.route) },
                onLogoutClick = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.UsersManagementScreen.route) {
            ManageUsersScreen(
                userRepo = userRepository,
                onNavigateBackToAdmin = { navController.popBackStack() },
                onLogoutClick = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                }
            )
        }

        composable(AppScreens.ReviewsManagementScreen.route) {
            ManageReviewsScreen(
                reviewRepo = reviewRepository,
                onNavigateBackPanel = { navController.popBackStack() },
                onLogoutClick = {
                    UserSession.currentUser = null
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
    object AdminScreen : AppScreens("admin_screen")
    object ModeradorScreen : AppScreens("moderador_screen")
    object UsersManagementScreen : AppScreens("users_management_screen")
    object ReviewsManagementScreen : AppScreens("reviews_management_screen")
    object ReviewScreen : AppScreens("review_screen/{movieId}") {
        fun createRoute(movieId: Int) = "review_screen/$movieId"
    }
    object FeedbackScreen : AppScreens("feedback_screen") // Nueva ruta para Feedback
}
