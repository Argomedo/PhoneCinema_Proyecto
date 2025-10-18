package com.example.phonecinemaapp.ui.perfil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonecinemaapp.data.local.database.AppDatabase
import com.example.phonecinemaapp.data.repository.UserRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    onBackClick: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val database = AppDatabase.getInstance(context)
    val userRepository = remember { UserRepository(database.userDao()) }
    val perfilViewModel: PerfilViewModel = viewModel(factory = PerfilViewModelFactory(userRepository))
    val uiState by perfilViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        perfilViewModel.cargarUsuario("test@test.com") // usuario simulado
    }

    if (uiState.isLoggedOut) {
        onLogout()
        return
    }

    PerfilContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onSave = perfilViewModel::guardarCambios,
        onNombreChange = perfilViewModel::onNombreChange,
        onEmailChange = perfilViewModel::onEmailChange,
        onLogout = perfilViewModel::logout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilContent(
    uiState: PerfilUiState,
    onBackClick: () -> Unit,
    onSave: () -> Unit,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFD4A106))
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
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
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onSave, modifier = Modifier.fillMaxWidth()) {
                Text("Guardar Cambios")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onLogout, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red, contentColor = Color.White
            )) {
                Text("Cerrar Sesión")
            }
        }
    }
}
