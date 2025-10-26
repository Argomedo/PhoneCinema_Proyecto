package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.phonecinemaapp.ui.components.AppTopBar
import com.example.phonecinemaapp.ui.theme.PhoneCinemaBlue
import com.example.phonecinemaapp.ui.theme.PhoneCinemaYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navController: NavController,
    onNavigateToUsers: () -> Unit,
    onNavigateToReviews: () -> Unit,
    onBackClick: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Panel del Administrador",
                navController = navController,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = onLogout
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Funciones del Administrador",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFAFAFA) // blanco suave
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón principal - azul PhoneCinema
            Button(
                onClick = onNavigateToUsers,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PhoneCinemaYellow,
                    contentColor = Color.White
                )
            ) {
                Text("Administrar Usuarios", color = Color(0xFF253B76))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón secundario - amarillo PhoneCinema
            Button(
                onClick = onNavigateToReviews,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PhoneCinemaYellow,
                    contentColor = Color.Black
                )
            ) {
                Text("Revisar / Eliminar Reseñas", color = Color(0xFF253B76))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón de cierre de sesión - rojo institucional
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB23A48),
                    contentColor = Color.White
                )
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}
