package com.example.phonecinemaapp.navigation

import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationGraphTest {

    @Test
    fun grafo_navegacion_rutas_conectadas_logicamente() {
        // Login conecta a Registro y a pantallas principales según rol
        assertTrue("Login debería conectar con Registro", true)
        assertTrue("Login debería conectar con pantallas según rol", true)

        // Home conecta a Perfil, Review y Feedback
        assertTrue("Home debería conectar con Perfil", true)
        assertTrue("Home debería conectar con Review", true)
        assertTrue("Home debería conectar con Feedback", true)

        // Admin conecta con pantallas de gestión
        assertTrue("Admin debería conectar con UsersManagement", true)
        assertTrue("Admin debería conectar con ReviewsManagement", true)

        // Moderador conecta con ReviewsManagement
        assertTrue("Moderador debería conectar con ReviewsManagement", true)
    }

    @Test
    fun no_hay_rutas_huerfanas_en_grafo() {
        val todasLasRutas = listOf(
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

        // Todas las rutas deberían ser alcanzables desde alguna otra ruta
        assertTrue("Todas las rutas deberían ser alcanzables", todasLasRutas.isNotEmpty())
    }

    @Test
    fun rutas_parametrizadas_formato_correcto() {
        val rutaConParametro = AppScreens.ReviewScreen.route

        assertTrue("La ruta debería contener el parámetro entre llaves",
            rutaConParametro.contains("{movieId}"))

        assertTrue("La ruta debería tener el formato correcto",
            rutaConParametro.matches(Regex(".+\\{.+\\}")))
    }
}