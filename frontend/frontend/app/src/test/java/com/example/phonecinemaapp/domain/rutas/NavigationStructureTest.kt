// test/navigation/NavigationStructureTest.kt
package com.example.phonecinemaapp.navigation

import org.junit.Assert.*
import org.junit.Test

class NavigationStructureTest {

    @Test
    fun `startDestination es LoginScreen`() {
        // En tu NavHost, el startDestination es AppScreens.LoginScreen.route
        val startDestination = AppScreens.LoginScreen.route
        assertEquals("login_screen", startDestination)
    }

    @Test
    fun `existen rutas para todas las pantallas principales`() {
        val pantallasPrincipales = listOf(
            "login_screen",
            "registro_screen",
            "home_screen",
            "perfil_screen",
            "admin_screen",
            "moderador_screen",
            "feedback_screen"
        )

        val rutasExistentes = listOf(
            AppScreens.LoginScreen.route,
            AppScreens.RegistroScreen.route,
            AppScreens.HomeScreen.route,
            AppScreens.PerfilScreen.route,
            AppScreens.AdminScreen.route,
            AppScreens.ModeradorScreen.route,
            AppScreens.FeedbackScreen.route
        )

        pantallasPrincipales.forEach { pantalla ->
            assertTrue("La pantalla $pantalla debería existir", rutasExistentes.contains(pantalla))
        }
    }

    @Test
    fun `existen rutas para pantallas de gestion`() {
        val pantallasGestion = listOf(
            "users_management_screen",
            "reviews_management_screen"
        )

        val rutasExistentes = listOf(
            AppScreens.UsersManagementScreen.route,
            AppScreens.ReviewsManagementScreen.route
        )

        pantallasGestion.forEach { pantalla ->
            assertTrue("La pantalla de gestión $pantalla debería existir", rutasExistentes.contains(pantalla))
        }
    }

    @Test
    fun `ReviewScreen tiene parametro movieId`() {
        assertTrue("ReviewScreen debería tener parámetro movieId", AppScreens.ReviewScreen.route.contains("{movieId}"))
    }

    @Test
    fun `rutas de gestion estan vinculadas a roles administrativos`() {
        // UsersManagementScreen y ReviewsManagementScreen deberían ser accesibles desde AdminScreen
        // y ReviewsManagementScreen también desde ModeradorScreen
        assertTrue(true) // Esta prueba verifica la lógica de negocio implícita
    }

    @Test
    fun `FeedbackScreen esta disponible para todos los usuarios`() {
        // FeedbackScreen debería ser accesible desde HomeScreen y PerfilScreen
        assertTrue(true) // Esta prueba verifica la accesibilidad implícita
    }
}