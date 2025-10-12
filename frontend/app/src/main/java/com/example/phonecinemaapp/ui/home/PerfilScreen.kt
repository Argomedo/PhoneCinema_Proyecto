package com.example.phonecinemaapp.ui.perfil

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.phonecinemaapp.R
import com.example.phonecinemaapp.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun PerfilScreen(
    onBackClick: () -> Unit,
    onLogout: () -> Unit = {},
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val uiState by perfilViewModel.uiState.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) onLogout()
    }

    // Manejar el botón de retroceso
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú principal",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Divider()

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onBackClick()
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = true,
                    onClick = {
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        perfilViewModel.logout()
                    }
                )
            }
        }
    ) {
        PerfilScreenContent(
            uiState = uiState,
            onBackClick = onBackClick,
            onSaveChanges = { perfilViewModel.guardarCambios() },
            onNombreChange = { perfilViewModel.onNombreChange(it) },
            onEmailChange = { perfilViewModel.onEmailChange(it) },
            onMenuClick = { scope.launch { drawerState.open() } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreenContent(
    uiState: PerfilUiState,
    onBackClick: () -> Unit,
    onSaveChanges: () -> Unit,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onMenuClick: () -> Unit
) {
    val isLightTheme = !isSystemInDarkTheme()
    val backgroundColor = if (isLightTheme) LightBackground else DarkBackground

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Perfil")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD4A106),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
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
                onValueChange = onNombreChange,
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
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
                onClick = onSaveChanges,
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
        onLogout = {}
    )
}