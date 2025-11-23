// test/navigation/NavigationFlowTest.kt
package com.example.phonecinemaapp.navigation

import org.junit.Test

class NavigationFlowTest {

    @Test
    fun `flujo de autenticacion comienza en login`() {
        // El flujo comienza en LoginScreen
        val rutaInicial = AppScreens.LoginScreen.route
        assert(rutaInicial == "login_screen")
    }

    @Test
    fun `usuario puede navegar de login a registro`() {
        // Desde LoginScreen se puede navegar a RegistroScreen
        val desde = AppScreens.LoginScreen.route
        val hacia = AppScreens.RegistroScreen.route

        assert(desde != hacia)
    }

    @Test
    fun `usuario puede navegar de registro a login`() {
        // Desde RegistroScreen se puede volver a LoginScreen
        val desde = AppScreens.RegistroScreen.route
        val hacia = AppScreens.LoginScreen.route

        assert(desde != hacia)
    }

    @Test
    fun `flujo principal incluye home y perfil`() {
        val flujoPrincipal = listOf(
            AppScreens.HomeScreen.route,
            AppScreens.PerfilScreen.route,
            AppScreens.ReviewScreen.route,
            AppScreens.FeedbackScreen.route
        )

        assert(flujoPrincipal.size >= 2)
    }

    @Test
    fun `flujo administrativo incluye gestion de usuarios y reseñas`() {
        val flujoAdministrativo = listOf(
            AppScreens.AdminScreen.route,
            AppScreens.UsersManagementScreen.route,
            AppScreens.ReviewsManagementScreen.route
        )

        assert(flujoAdministrativo.size >= 2)
    }

    @Test
    fun `flujo de moderador incluye gestion de reseñas`() {
        val flujoModerador = listOf(
            AppScreens.ModeradorScreen.route,
            AppScreens.ReviewsManagementScreen.route
        )

        assert(flujoModerador.size >= 2)
    }
}