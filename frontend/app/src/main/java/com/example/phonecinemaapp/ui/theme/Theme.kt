package com.example.phonecinemaapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PhoneCinemaYellow,      // Color de acento
    background = PhoneCinemaBlue,     // Fondo de pantallas
    surface = PhoneCinemaBlue,        // Color de superficies como la TopAppBar
    onPrimary = Color.Black,          // Texto sobre amarillo
    onBackground = Color.White,       // Texto sobre azul
    onSurface = Color.White           // Texto sobre superficies azules
)

@Composable
fun PhoneCinemaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),

    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme // Forzamos el tema oscuro siempre
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}