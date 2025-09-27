package com.example.phonecinemaapp.ui.registro

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistroScreen(
    registroViewModel: RegistroViewModel = viewModel(),
    onNavigateToLogin: () -> Unit
) {
    val uiState by registroViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.registroExitoso) {
        if (uiState.registroExitoso) {
            Toast.makeText(context, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
            onNavigateToLogin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crear Cuenta", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.nombre,
            onValueChange = { registroViewModel.onRegistroChange(it, uiState.email, uiState.contrasena, uiState.confirmarContrasena ) },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.errorMensaje != null, // Se pone en rojo si hay error
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray,
                errorIndicatorColor = MaterialTheme.colorScheme.error // Color del borde en error
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { registroViewModel.onRegistroChange(uiState.nombre, it, uiState.contrasena, uiState.confirmarContrasena) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.errorMensaje != null, // Se pone en rojo si hay error
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.contrasena,
            onValueChange = { registroViewModel.onRegistroChange(uiState.nombre, uiState.email, it, uiState.confirmarContrasena) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.errorMensaje != null, // Se pone en rojo si hay error
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.confirmarContrasena,
            onValueChange = { registroViewModel.onRegistroChange(uiState.nombre, uiState.email, uiState.contrasena, it) },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = uiState.errorMensaje != null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // MUESTRA EL MENSAJE DE ERROR SI EXISTE
        uiState.errorMensaje?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { registroViewModel.registrarUsuario() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes una cuenta? Inicia sesión")
        }
    }
}