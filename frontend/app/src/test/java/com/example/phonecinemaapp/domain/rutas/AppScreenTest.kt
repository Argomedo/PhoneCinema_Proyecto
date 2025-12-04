package com.example.phonecinemaapp.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppScreensTest {

    @Test
    fun AppScreens_rutas_correctas() {
        assertEquals("login_screen", AppScreens.LoginScreen.route)
        assertEquals("registro_screen", AppScreens.RegistroScreen.route)
        assertEquals("home_screen", AppScreens.HomeScreen.route)
        assertEquals("perfil_screen", AppScreens.PerfilScreen.route)
        assertEquals("admin_screen", AppScreens.AdminScreen.route)
        assertEquals("moderador_screen", AppScreens.ModeradorScreen.route)
        assertEquals("users_management_screen", AppScreens.UsersManagementScreen.route)
        assertEquals("reviews_management_screen", AppScreens.ReviewsManagementScreen.route)
        assertEquals("review_screen/{movieId}", AppScreens.ReviewScreen.route)
        assertEquals("feedback_screen", AppScreens.FeedbackScreen.route)
    }

    @Test
    fun ReviewScreen_createRoute_genera_ruta_correcta() {
        val movieId = 123
        val expectedRoute = "review_screen/123"
        val actualRoute = AppScreens.ReviewScreen.createRoute(movieId)

        assertEquals(expectedRoute, actualRoute)
    }

    @Test
    fun ReviewScreen_createRoute_con_diferentes_IDs() {
        assertEquals("review_screen/1", AppScreens.ReviewScreen.createRoute(1))
        assertEquals("review_screen/999", AppScreens.ReviewScreen.createRoute(999))
        assertEquals("review_screen/0", AppScreens.ReviewScreen.createRoute(0))
        assertEquals("review_screen/-1", AppScreens.ReviewScreen.createRoute(-1))
    }

    @Test
    fun AppScreens_todas_rutas_unicas() {
        val routes = listOf(
            AppScreens.LoginScreen.route,
            AppScreens.RegistroScreen.route,
            AppScreens.HomeScreen.route,
            AppScreens.PerfilScreen.route,
            AppScreens.AdminScreen.route,
            AppScreens.ModeradorScreen.route,
            AppScreens.UsersManagementScreen.route,
            AppScreens.ReviewsManagementScreen.route,
            AppScreens.ReviewScreen.route,
            AppScreens.FeedbackScreen.route
        )

        val uniqueRoutes = routes.distinct()
        assertEquals(routes.size, uniqueRoutes.size)
    }

    @Test
    fun AppScreens_rutas_sin_espacios() {
        val allScreens = listOf(
            AppScreens.LoginScreen,
            AppScreens.RegistroScreen,
            AppScreens.HomeScreen,
            AppScreens.PerfilScreen,
            AppScreens.AdminScreen,
            AppScreens.ModeradorScreen,
            AppScreens.UsersManagementScreen,
            AppScreens.ReviewsManagementScreen,
            AppScreens.ReviewScreen,
            AppScreens.FeedbackScreen
        )

        allScreens.forEach { screen ->
            val hasSpaces = screen.route.contains(" ")
            assertFalse(hasSpaces)
        }
    }

    @Test
    fun AppScreens_rutas_formato_consistente() {
        val allScreens = listOf(
            AppScreens.LoginScreen,
            AppScreens.RegistroScreen,
            AppScreens.HomeScreen,
            AppScreens.PerfilScreen,
            AppScreens.AdminScreen,
            AppScreens.ModeradorScreen,
            AppScreens.UsersManagementScreen,
            AppScreens.ReviewsManagementScreen,
            AppScreens.ReviewScreen,
            AppScreens.FeedbackScreen
        )

        val pattern = Regex("[a-z_/{}]+")
        allScreens.forEach { screen ->
            val matchesPattern = pattern.matches(screen.route)
            assertTrue(matchesPattern)
        }
    }
}