package com.example.phonecinemaapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Usamos los colores de nuestra marca
private val DarkColorScheme = darkColorScheme(
    primary = PhoneCinemaYellow,
    background = PhoneCinemaBlue,
    surface = PhoneCinemaBlue
)

private val LightColorScheme = lightColorScheme(
    primary = PhoneCinemaYellow,
    background = PhoneCinemaBlue,
    surface = PhoneCinemaBlue
)

// Corregimos el nombre de la función a "PhoneCinemaTheme"
@Composable
fun PhoneCinemaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}