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
import com.example.phonecinema.data.remote.UserApi
import com.example.phonecinema.data.remote.PeliculaApi
import com.example.phonecinema.data.remote.RemoteModule

import com.example.phonecinemaapp.data.repository.PeliculasRepositoryRemote
import com.example.phonecinema.data.repository.ReviewRepository

import com.example.phonecinemaapp.data.repository.AuthRepository
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.data.session.UserSession

import com.example.phonecinemaapp.ui.feedback.FeedbackScreen
import com.example.phonecinemaapp.ui.feedback.FeedbackViewModel

import com.example.phonecinemaapp.ui.home.HomeScreen
import com.example.phonecinemaapp.ui.home.HomeViewModel
import com.example.phonecinemaapp.ui.home.HomeViewModelFactory

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

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // APIs
    val authApi = RemoteModule.createUsuarios(AuthApi::class.java)
    val userApi = RemoteModule.createUsuarios(UserApi::class.java)
    val reviewApi = RemoteModule.createResenas(ReviewApi::class.java)
    val feedbackApi = RemoteModule.createFeedback(FeedbackApi::class.java)
    val peliculasApi = RemoteModule.createPeliculas(PeliculaApi::class.java)

    // Repositorios correctos
    val authRepository = AuthRepository()
    val userRepository = UserRepository(userApi)
    val reviewRepository = ReviewRepository(reviewApi)
    val peliculasRepository = PeliculasRepositoryRemote(peliculasApi)

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {

        // LOGIN
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

        // REGISTRO
        composable(AppScreens.RegistroScreen.route) {
            val vm = RegistroViewModel(userRepository)
            RegistroScreen(
                registroViewModel = vm,
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // HOME – usa HomeViewModelFactory + PeliculasRepositoryRemote
        composable(AppScreens.HomeScreen.route) {

            val homeVm: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(peliculasRepository)
            )

            HomeScreen(
                homeViewModel = homeVm,
                onLogout = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                },
                onNavigateToMovieDetails = { movieId ->
                    navController.navigate(AppScreens.ReviewScreen.createRoute(movieId))
                },
                onNavigateToProfile = { navController.navigate(AppScreens.PerfilScreen.route) },
                onNavigateToFeedback = { navController.navigate(AppScreens.FeedbackScreen.route) }
            )
        }

        // REVIEW
        composable(
            AppScreens.ReviewScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->

            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1

            ReviewScreen(
                navController = navController,
                movieId = movieId,
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                }
            )
        }

        // PERFIL
        composable(AppScreens.PerfilScreen.route) {
            val id = UserSession.currentUser?.id ?: return@composable run {
                navController.navigate(AppScreens.LoginScreen.route)
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

        // FEEDBACK
        composable(AppScreens.FeedbackScreen.route) {
            val vm: FeedbackViewModel = viewModel()
            FeedbackScreen(viewModel = vm, navController = navController)
        }

        // ADMIN
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

        // MODERADOR
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

        // USERS
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

        // REVIEWS ADMIN/MOD
        composable(AppScreens.ReviewsManagementScreen.route) {
            ManageReviewsScreen(
                reviewRepo = reviewRepository,
                peliculasRepo = peliculasRepository,
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

    object FeedbackScreen : AppScreens("feedback_screen")
}
