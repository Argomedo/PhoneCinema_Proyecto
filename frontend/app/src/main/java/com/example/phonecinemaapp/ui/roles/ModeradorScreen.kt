package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.phonecinemaapp.ui.components.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModeradorScreen(
    navController: NavController,
    onNavigateToReviews: () -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Panel del Moderador",
                navController = navController,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = onLogoutClick
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
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToReviews,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text("Revisar / Eliminar Reseñas")
            }
        }
    }
}
