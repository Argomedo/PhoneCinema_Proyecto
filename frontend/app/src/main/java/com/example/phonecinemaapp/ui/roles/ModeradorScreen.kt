package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
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
fun ModeradorScreen(
    navController: NavController,
    onNavigateToReviews: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel del Moderador", color = Color.White) },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = Color(0xFFB23A48)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PhoneCinemaYellow
                )
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
                text = "Funciones del Moderador",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFAFAFA)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToReviews,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PhoneCinemaYellow)
            ) {
                Text("Revisar / Eliminar Reseñas", color = Color(0xFF253B76))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB23A48))
            ) {
                Text("Cerrar Sesión", color = Color.White)
            }
        }
    }
}


