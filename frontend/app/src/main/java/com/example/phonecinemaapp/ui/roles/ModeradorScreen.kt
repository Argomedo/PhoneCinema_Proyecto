package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeradorScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Moderador") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Funciones del Moderador",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { /* eliminar reseñas inapropiadas */ },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Eliminar Reseñas Inapropiadas") }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { /* revisar reportes */ },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Revisar Reportes") }
        }
    }
}
