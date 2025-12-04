package com.example.phonecinemaapp.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorsTest {

    @Test
    fun colores_de_marca_tienen_valores_correctos() {
        val blue = PhoneCinemaBlue
        val yellow = PhoneCinemaYellow

        assertEquals(Color(0xFF253B76), blue)
        assertEquals(Color(0xFFFFC107), yellow)
    }

    @Test
    fun colores_de_tema_oscuro_estan_definidos() {
        val background = DarkBackground
        val surface = DarkSurface
        val textPrimary = TextOnDark
        val textSecondary = TextOnDarkSecondary

        assertEquals(Color(0xFF121212), background)
        assertEquals(Color(0xFF1E1E1E), surface)
        assertEquals(Color(0xFFE0E0E0), textPrimary)
        assertEquals(Color(0xFFB0B0B0), textSecondary)
    }

    @Test
    fun colores_de_tema_claro_estan_definidos() {
        val background = LightBackground
        val surface = LightSurface
        val text = TextOnLight

        assertEquals(Color(0xFFFFFFFF), background)
        assertEquals(Color(0xFFF5F5F5), surface)
        assertEquals(Color(0xFF000000), text)
    }
}