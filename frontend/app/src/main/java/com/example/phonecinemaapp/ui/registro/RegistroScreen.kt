package com.example.phonecinemaapp.ui.registro

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.phonecinemaapp.domain.validation.*

@Composable
fun RegistroScreen(
    registroViewModel: RegistroViewModel,
    onNavigateToLogin: () -> Unit
) {
    val uiState by registroViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.registroExitoso) {
        if (uiState.registroExitoso) {
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
            onNavigateToLogin()
        }
    }

    RegistroContent(
        nombre = uiState.nombre,
        email = uiState.email,
        password = uiState.contrasena,
        confirm = uiState.confirmarContrasena,
        error = uiState.errorMensaje,
        onNombreChange = registroViewModel::onNameChange,
        onEmailChange = registroViewModel::onEmailChange,
        onPasswordChange = registroViewModel::onPasswordChange,
        onConfirmChange = registroViewModel::onConfirmPasswordChange,
        onRegisterClick = registroViewModel::registrarUsuario,
        onLoginClick = onNavigateToLogin
    )
}

@Composable
private fun RegistroContent(
    nombre: String,
    email: String,
    password: String,
    confirm: String,
    error: String?,
    onNombreChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
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
        Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium, color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))

        // Nombre (siempre habilitado)
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = error?.contains("nombre", ignoreCase = true) == true
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Email habilitado solo si nombre válido
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Correo electrónico") },
            singleLine = true,
            enabled = validateName(nombre) == null,
            modifier = Modifier.fillMaxWidth(),
            isError = error?.contains("correo", ignoreCase = true) == true ||
                    error?.contains("email", ignoreCase = true) == true
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Contraseña habilitada solo si email válido
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPass = !showPass }) {
                    Icon(
                        imageVector = if (showPass) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            enabled = validateName(nombre) == null &&
                    validateEmail(email) == null,
            modifier = Modifier.fillMaxWidth(),
            isError = error?.contains("contraseña", ignoreCase = true) == true
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Confirmación habilitada solo si contraseña válida
        OutlinedTextField(
            value = confirm,
            onValueChange = onConfirmChange,
            label = { Text("Confirmar contraseña") },
            visualTransformation = if (showConfirm) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showConfirm = !showConfirm }) {
                    Icon(
                        imageVector = if (showConfirm) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null
                    )
                }
            },
            singleLine = true,
            enabled = validateName(nombre) == null &&
                    validateEmail(email) == null &&
                    validatePassword(password) == null,
            modifier = Modifier.fillMaxWidth(),
            isError = error?.contains("coinciden", ignoreCase = true) == true
        )

        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = error == null || error.isBlank()
        ) {
            Text("Registrar", color = Color(0xFF253B76))
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onLoginClick) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}
