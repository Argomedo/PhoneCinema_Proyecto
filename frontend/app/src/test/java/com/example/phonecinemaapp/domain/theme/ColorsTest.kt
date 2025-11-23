// test/ui/theme/ColorsTest.kt
package com.example.phonecinemaapp.ui.theme

import androidx.compose.ui.graphics.Color
import org.junit.Assert.*
import org.junit.Test

class ColorsTest {

    @Test
    fun `colores de marca tienen valores correctos`() {
        assertEquals(Color(0xFF253B76), PhoneCinemaBlue)
        assertEquals(Color(0xFFFFC107), PhoneCinemaYellow)
    }

    @Test
    fun `colores de tema oscuro estan definidos`() {
        assertEquals(Color(0xFF121212), DarkBackground)
        assertEquals(Color(0xFF1E1E1E), DarkSurface)
        assertEquals(Color(0xFFE0E0E0), TextOnDark)
        assertEquals(Color(0xFFB0B0B0), TextOnDarkSecondary)
    }

    @Test
    fun `colores de tema claro estan definidos`() {
        assertEquals(Color(0xFFFFFFFF), LightBackground)
        assertEquals(Color(0xFFF5F5F5), LightSurface)
        assertEquals(Color(0xFF000000), TextOnLight)
    }
}