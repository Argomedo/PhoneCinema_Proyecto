package com.example.phonecinemaapp.ui.perfil

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.phonecinemaapp.R
import com.example.phonecinemaapp.ui.components.AppTopBar
import com.example.phonecinemaapp.ui.theme.*

@Composable
fun PerfilScreen(
    onBackClick: () -> Unit,
    onLogout: () -> Unit = {},
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val uiState by perfilViewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) onLogout()
    }

    val isLightTheme = !isSystemInDarkTheme()
    val backgroundColor = if (isLightTheme) LightBackground else DarkBackground
    val textColor = if (isLightTheme) TextOnLight else TextOnDark

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Perfil",
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = { perfilViewModel.logout() }
            )
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Foto de perfil con fondo amarillo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(PhoneCinemaYellow),
                contentAlignment = Alignment.Center
            ) {
                val painter = if (uiState.fotoUrl.isNotEmpty()) {
                    rememberAsyncImagePainter(model = uiState.fotoUrl)
                } else {
                    painterResource(id = R.drawable.ic_perfil_logo)
                }

                Image(
                    painter = painter,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { perfilViewModel.onNombreChange(it) },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { perfilViewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            uiState.errorMensaje?.let { mensaje ->
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = { perfilViewModel.guardarCambios() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PhoneCinemaBlue,
                    contentColor = LightBackground
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen(
        onBackClick = {},
        onLogout = {},
        perfilViewModel = PerfilViewModel()
    )
}
