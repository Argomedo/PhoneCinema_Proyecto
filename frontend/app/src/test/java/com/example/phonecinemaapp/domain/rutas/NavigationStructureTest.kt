package com.example.phonecinemaapp.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationStructureTest {

    @Test
    fun startDestination_correcto() {
        assertEquals("login_screen", AppScreens.LoginScreen.route)
    }

    @Test
    fun pantallas_principales_definidas() {
        assertEquals("login_screen", AppScreens.LoginScreen.route)
        assertEquals("registro_screen", AppScreens.RegistroScreen.route)
        assertEquals("home_screen", AppScreens.HomeScreen.route)
        assertEquals("perfil_screen", AppScreens.PerfilScreen.route)
        assertEquals("admin_screen", AppScreens.AdminScreen.route)
        assertEquals("moderador_screen", AppScreens.ModeradorScreen.route)
        assertEquals("feedback_screen", AppScreens.FeedbackScreen.route)
    }

    @Test
    fun pantallas_gestion_definidas() {
        assertEquals("users_management_screen", AppScreens.UsersManagementScreen.route)
        assertEquals("reviews_management_screen", AppScreens.ReviewsManagementScreen.route)
    }

    @Test
    fun ReviewScreen_parametrizada() {
        val ruta = AppScreens.ReviewScreen.route
        assertTrue(ruta.contains("{movieId}"))
        assertEquals("review_screen/{movieId}", ruta)
    }
}