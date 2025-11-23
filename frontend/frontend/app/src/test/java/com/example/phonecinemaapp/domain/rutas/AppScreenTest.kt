// test/navigation/AppScreensTest.kt
package com.example.phonecinemaapp.navigation

import org.junit.Assert.*
import org.junit.Test

class AppScreensTest {

    @Test
    fun `AppScreens tienen rutas correctas`() {
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
    fun `ReviewScreen createRoute genera ruta correcta`() {
        val movieId = 123
        val expectedRoute = "review_screen/123"
        assertEquals(expectedRoute, AppScreens.ReviewScreen.createRoute(movieId))
    }

    @Test
    fun `ReviewScreen createRoute con diferentes IDs`() {
        assertEquals("review_screen/1", AppScreens.ReviewScreen.createRoute(1))
        assertEquals("review_screen/999", AppScreens.ReviewScreen.createRoute(999))
        assertEquals("review_screen/0", AppScreens.ReviewScreen.createRoute(0))
        assertEquals("review_screen/-1", AppScreens.ReviewScreen.createRoute(-1))
    }

    @Test
    fun `todas las rutas son unicas`() {
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
        assertEquals("Todas las rutas deben ser únicas", routes.size, uniqueRoutes.size)
    }

    @Test
    fun `rutas no contienen espacios`() {
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
            assertFalse("La ruta no debe contener espacios: ${screen.route}", screen.route.contains(" "))
        }
    }

    @Test
    fun `rutas tienen formato consistente`() {
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
            assertTrue(
                "La ruta debe estar en minúsculas y usar underscores: ${screen.route}",
                screen.route.matches(Regex("[a-z_/{}]+"))
            )
        }
    }
}