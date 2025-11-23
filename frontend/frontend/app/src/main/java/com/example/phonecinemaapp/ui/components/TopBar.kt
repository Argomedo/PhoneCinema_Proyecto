package com.example.phonecinemaapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info  // Ícono para el feedback
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.phonecinemaapp.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    navController: NavController,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onLogoutClick: () -> Unit,
    onFeedbackClick: () -> Unit  // Nueva acción para redirigir a la pantalla de feedback
) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val white = Color(0xFFFAFAFA)

    TopAppBar(
        title = { Text(text = title, color = white) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = white
                    )
                }
            } else {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = white
                    )
                }
            }
        },
        actions = {
            // Botón para navegar a la pantalla de perfil
            IconButton(onClick = {
                if (currentRoute != AppScreens.PerfilScreen.route) {
                    navController.navigate(AppScreens.PerfilScreen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = white
                )
            }

            // Botón para ir a la pantalla de Feedback
            IconButton(onClick = onFeedbackClick) {
                Icon(
                    imageVector = Icons.Default.Info,  // Ícono para feedback
                    contentDescription = "Feedback",
                    tint = white
                )
            }

            // Botón de cerrar sesión
            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = white
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF253B76), // Color de fondo de la barra superior
            titleContentColor = white,
            navigationIconContentColor = white,
            actionIconContentColor = white
        )
    )
}
