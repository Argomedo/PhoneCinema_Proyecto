package com.example.phonecinemaapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

//Hice el top bar denuevo para acodar a las nuevas funciones
//Pero falta el buscador

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showBackButton: Boolean = false, // Para decidir si muestras la flecha
    onBackClick: () -> Unit = {}, // Acción para la flecha
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    TopAppBar(
        title = {
            Text(
                text = title, // Título dinámico
                color = colors.onPrimary
            )
        },
        navigationIcon = {
            // Lógica para mostrar flecha o menú
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
                        contentDescription = "Abrir menú",
                        tint = colors.onPrimary
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onProfileClick){
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil de usuario",
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