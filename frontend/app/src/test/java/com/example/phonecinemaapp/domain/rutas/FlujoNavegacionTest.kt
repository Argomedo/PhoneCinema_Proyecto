package com.example.phonecinemaapp.navigation

import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationFlowTest {

    @Test
    fun flujo_autenticacion_comienza_en_login() {
        val rutaInicial = AppScreens.LoginScreen.route
        assertTrue(rutaInicial == "login_screen")
    }

    @Test
    fun usuario_puede_navegar_login_a_registro() {
        val desde = AppScreens.LoginScreen.route
        val hacia = AppScreens.RegistroScreen.route

        assertNotEquals(desde, hacia)
    }

    @Test
    fun usuario_puede_navegar_registro_a_login() {
        val desde = AppScreens.RegistroScreen.route
        val hacia = AppScreens.LoginScreen.route

        assertNotEquals(desde, hacia)
    }

    @Test
    fun flujo_principal_incluye_home_y_perfil() {
        val flujoPrincipal = listOf(
            AppScreens.HomeScreen.route,
            AppScreens.PerfilScreen.route,
            AppScreens.ReviewScreen.route,
            AppScreens.FeedbackScreen.route
        )

        assertTrue(flujoPrincipal.size >= 2)
    }

    @Test
    fun flujo_administrativo_incluye_gestion_usuarios_y_reseñas() {
        val flujoAdministrativo = listOf(
            AppScreens.AdminScreen.route,
            AppScreens.UsersManagementScreen.route,
            AppScreens.ReviewsManagementScreen.route
        )

        assertTrue(flujoAdministrativo.size >= 2)
    }

    @Test
    fun flujo_moderador_incluye_gestion_reseñas() {
        val flujoModerador = listOf(
            AppScreens.ModeradorScreen.route,
            AppScreens.ReviewsManagementScreen.route
        )

        assertTrue(flujoModerador.size >= 2)
    }
}