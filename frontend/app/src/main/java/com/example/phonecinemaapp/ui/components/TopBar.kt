package com.example.phonecinemaapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
    onFeedbackClick: () -> Unit
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

            // ICONO PERFIL: se oculta si ya estoy en PerfilScreen
            if (currentRoute != AppScreens.PerfilScreen.route) {
                IconButton(onClick = {
                    navController.navigate(AppScreens.PerfilScreen.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil",
                        tint = white
                    )
                }
            }

            // ICONO FEEDBACK
            IconButton(onClick = onFeedbackClick) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Feedback",
                    tint = white
                )
            }

            // ICONO LOGOUT
            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = white
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF253B76),
            titleContentColor = white,
            navigationIconContentColor = white,
            actionIconContentColor = white
        )
    )
}
