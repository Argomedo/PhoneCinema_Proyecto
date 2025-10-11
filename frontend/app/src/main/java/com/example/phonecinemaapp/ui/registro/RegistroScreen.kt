package com.example.phonecinemaapp.ui.registro

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel



// Composable público que maneja el ViewModel y la navegación
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

    RegistroScreenContent(
        nombreState = uiState.nombre,
        emailState = uiState.email,
        passwordState = uiState.contrasena,
        confirmPasswordState = uiState.confirmarContrasena,
        errorMensajeState = uiState.errorMensaje,
        onNombreChange = { nombre -> registroViewModel.onRegistroChange(nombre, uiState.email, uiState.contrasena, uiState.confirmarContrasena) },
        onEmailChange = { email -> registroViewModel.onRegistroChange(uiState.nombre, email, uiState.contrasena, uiState.confirmarContrasena) },
        onPasswordChange = { pass -> registroViewModel.onRegistroChange(uiState.nombre, uiState.email, pass, uiState.confirmarContrasena) },
        onConfirmPasswordChange = { confirm -> registroViewModel.onRegistroChange(uiState.nombre, uiState.email, uiState.contrasena, confirm) },
        onRegisterClick = { registroViewModel.registrarUsuario() },
        onNavigateToLoginClick = onNavigateToLogin
    )
}

// Composable privado que dibuja la UI
@Composable
private fun RegistroScreenContent(
    nombreState: String,
    emailState: String,
    passwordState: String,
    confirmPasswordState: String,
    errorMensajeState: String?,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateToLoginClick: () -> Unit
) {
    var showPass by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

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
            value = nombreState,
            onValueChange = onNombreChange,
            label = { Text("Nombre de usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = errorMensajeState != null
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = emailState,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = errorMensajeState != null
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordState,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPass = !showPass }) {
                    val icon = if (showPass) androidx.compose.material.icons.Icons.Filled.VisibilityOff else androidx.compose.material.icons.Icons.Filled.Visibility
                    Icon(icon, contentDescription = "Mostrar/Ocultar contraseña")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMensajeState != null
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPasswordState,
            onValueChange = onConfirmPasswordChange,
            label = { Text("Confirmar Contraseña") },
            singleLine = true,
            visualTransformation = if (showConfirm) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showConfirm = !showConfirm }) {
                    val icon = if (showConfirm) androidx.compose.material.icons.Icons.Filled.VisibilityOff else androidx.compose.material.icons.Icons.Filled.Visibility
                    Icon(icon, contentDescription = "Mostrar/Ocultar confirmación")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMensajeState != null
        )
        Spacer(modifier = Modifier.height(16.dp))

        errorMensajeState?.let { mensaje ->
            Text(text = mensaje, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToLoginClick) {
            Text("¿Ya tienes una cuenta? Inicia sesión")
        }
    }
}
