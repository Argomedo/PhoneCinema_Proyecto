package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administrador") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
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
                "Funciones del Administrador",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { /* gestionar usuarios */ },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Administrar Usuarios", color = Color.White) }


            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { /* asignar roles */ },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Asignar Roles", color = Color.White) }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { /* eliminar reseñas */ },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Revisar / Eliminar Reseñas", color = Color.White) }
        }
    }
}
