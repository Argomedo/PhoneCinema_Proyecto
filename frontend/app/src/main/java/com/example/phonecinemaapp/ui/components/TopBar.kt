package com.example.phonecinemaapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.phonecinemaapp.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    navController: NavController,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onLogoutClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    TopAppBar(
        title = { Text(text = title, color = colors.onPrimary) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = colors.onPrimary
                    )
                }
            } else {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menú",
                        tint = colors.onPrimary
                    )
                }
            }
        },
        actions = {
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
                    tint = colors.onPrimary
                )
            }

            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = colors.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.primary,
            titleContentColor = colors.onPrimary,
            navigationIconContentColor = colors.onPrimary,
            actionIconContentColor = colors.onPrimary
        )
    )
}
