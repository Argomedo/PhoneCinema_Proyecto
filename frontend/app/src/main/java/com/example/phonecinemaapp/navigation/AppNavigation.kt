package com.example.phonecinemaapp.navigation

import ReviewApi
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.phonecinema.data.remote.AuthApi
import com.example.phonecinema.data.remote.MovieApi
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


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    // === Remote services ===
    val authApi = RemoteModule.create(AuthApi::class.java)
    val userApi = RemoteModule.create(UserApi::class.java)
    val reviewApi = RemoteModule.create(ReviewApi::class.java)
    val movieApi = RemoteModule.create(MovieApi::class.java)

    // === Repositorios ===
    val authRepository = AuthRepository()
    val userRepository = UserRepository(userApi)
    val reviewRepository = ReviewRepository(reviewApi)
    // movieRepository si lo necesitas más adelante…

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
                    navController.navigate(AppScreens.HomeScreen.route) {
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

        // HOME
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(
                onLogout = {
                    UserSession.currentUser = null
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

// PERFIL
        composable(AppScreens.PerfilScreen.route) {

            val id = UserSession.currentUser?.id

            if (id == null) {
                navController.navigate(AppScreens.LoginScreen.route) {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                }
                return@composable
            }

            val perfilViewModel = PerfilViewModel(
                userRepository = userRepository,
                reviewRepository = reviewRepository
            )

            PerfilScreen(
                navController = navController,
                userId = id,
                perfilViewModel = perfilViewModel,
                onBackClick = { navController.popBackStack() },
                onLogout = {
                    UserSession.currentUser = null
                    navController.navigate(AppScreens.LoginScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                    }
                }
            )
        }



        // REVIEW
        composable(
            route = AppScreens.ReviewScreen.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
            val vm = ReviewViewModel(reviewRepository)

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

