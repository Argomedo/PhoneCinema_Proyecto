package com.example.phonecinemaapp.ui.registro

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// -----------------------------------------------------------------------------------
// PARTE 1: COMPOSABLE CON ESTADO (STATEFUL) - Gestiona la lógica y el ViewModel
// -----------------------------------------------------------------------------------

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

    // Llama al Composable sin estado, pasándole el estado actual y las acciones
    RegistroScreenContent(
        nombreState = uiState.nombre,
        emailState = uiState.email,
        passwordState = uiState.contrasena,
        confirmPasswordState = uiState.confirmarContrasena,
        errorMensajeState = uiState.errorMensaje,
        onNombreChange = { newNombre ->
            registroViewModel.onRegistroChange(newNombre, uiState.email, uiState.contrasena, uiState.confirmarContrasena)
        },
        onEmailChange = { newEmail ->
            registroViewModel.onRegistroChange(uiState.nombre, newEmail, uiState.contrasena, uiState.confirmarContrasena)
        },
        onPasswordChange = { newPassword ->
            registroViewModel.onRegistroChange(uiState.nombre, uiState.email, newPassword, uiState.confirmarContrasena)
        },
        onConfirmPasswordChange = { newConfirmPassword ->
            registroViewModel.onRegistroChange(uiState.nombre, uiState.email, uiState.contrasena, newConfirmPassword)
        },
        onRegisterClick = { registroViewModel.registrarUsuario() },
        onNavigateToLoginClick = onNavigateToLogin
    )
}


// -----------------------------------------------------------------------------------
// PARTE 2: COMPOSABLE SIN ESTADO (STATELESS) - Solo dibuja la UI
// -----------------------------------------------------------------------------------

@Composable
fun RegistroScreenContent(
    nombreState: String,
    emailState: String,
    passwordState: String,
    confirmPasswordState: String,
    errorMensajeState: String?, // El mensaje de error puede ser nulo
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateToLoginClick: () -> Unit
) {
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMensajeState != null,
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
            value = emailState,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMensajeState != null,
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
            value = passwordState,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMensajeState != null,
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
            value = confirmPasswordState,
            onValueChange = onConfirmPasswordChange,
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMensajeState != null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        errorMensajeState?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToLoginClick) {
            Text("¿Ya tienes una cuenta? Inicia sesión")
        }
    }
}


// -----------------------------------------------------------------------------------
// PARTE 3: PREVISUALIZACIONES - Para ver tu UI sin ejecutar la app
// -----------------------------------------------------------------------------------

@Preview(
    name = "Registro Screen - Normal",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RegistroScreenPreview() {
    RegistroScreenContent(
        nombreState = "Juan Perez",
        emailState = "juan.perez@email.com",
        passwordState = "123456",
        confirmPasswordState = "123456",
        errorMensajeState = null, // No hay error
        onNombreChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
        onNavigateToLoginClick = {}
    )
}

@Preview(
    name = "Registro Screen - Con Error",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RegistroScreenErrorPreview() {
    RegistroScreenContent(
        nombreState = "Juan Perez",
        emailState = "juan.perez@email.com",
        passwordState = "123456",
        confirmPasswordState = "contraseña-diferente",
        errorMensajeState = "Las contraseñas no coinciden.", // Simulamos un mensaje de error
        onNombreChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onRegisterClick = {},
        onNavigateToLoginClick = {}
    )
}