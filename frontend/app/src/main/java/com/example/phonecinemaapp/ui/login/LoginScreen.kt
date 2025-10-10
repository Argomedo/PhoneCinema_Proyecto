package com.example.phonecinemaapp.ui.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonecinemaapp.R


// PARTE 1: COMPOSABLE CON ESTADO (STATEFUL) - Gestiona la lógica y el ViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onLoginExitoso: () -> Unit,
    onNavigateToRegistro: () -> Unit
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.loginExitoso) {
        if (uiState.loginExitoso) {
            Toast.makeText(context, "¡Login exitoso!", Toast.LENGTH_SHORT).show()
            onLoginExitoso()
        }
    }

    // Llama al Composable sin estado, pasándole el estado actual y las acciones
    LoginScreenContent(
        emailState = uiState.email,
        passwordState = uiState.contrasena,
        onEmailChange = { email -> loginViewModel.onLoginChange(email, uiState.contrasena) },
        onPasswordChange = { pass -> loginViewModel.onLoginChange(uiState.email, pass) },
        onLoginClick = { loginViewModel.iniciarSesion() },
        onRegisterClick = onNavigateToRegistro,
        onForgotPasswordClick = { /* TODO: Implementar lógica de contraseña olvidada */ }
    )
}


// -----------------------------------------------------------------------------------
// PARTE 2: COMPOSABLE SIN ESTADO (STATELESS) - Solo dibuja la UI, es fácil de previsualizar
// -----------------------------------------------------------------------------------

@Composable
fun LoginScreenContent(
    emailState: String,
    passwordState: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo de PhoneCinema",
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = emailState,
            onValueChange = onEmailChange,
            label = { Text("Usuario o Correo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordState,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onForgotPasswordClick) {
                Text("¿Olvidaste tu Contraseña?", color = Color.White, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text("ENTRAR", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onRegisterClick) {
            Text("¿No tienes una cuenta? Regístrate aquí", color = Color.White)
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}


// -----------------------------------------------------------------------------------
// PARTE 3: PREVISUALIZACIONES - Para ver tu UI sin ejecutar la app
// -----------------------------------------------------------------------------------

@Preview(
    name = "Login Screen - Modo Oscuro",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F, // Color de fondo oscuro para que se vea bien
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        emailState = "usuario@ejemplo.com",
        passwordState = "12345678",
        onEmailChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onRegisterClick = {},
        onForgotPasswordClick = {}
    )
}

@Preview(
    name = "Login Screen - Campos Vacíos",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenEmptyPreview() {
    LoginScreenContent(
        emailState = "",
        passwordState = "",
        onEmailChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onRegisterClick = {},
        onForgotPasswordClick = {}
    )
}

@Preview(
    name = "Login Screen - En Dispositivo Pequeño",
    showBackground = true,
    backgroundColor = 0xFF1C1B1F,
    device = Devices.PIXEL_4A,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenSmallDevicePreview() {
    LoginScreenContent(
        emailState = "un.correo.muy.largo.para.probar@ejemplo.com",
        passwordState = "password123",
        onEmailChange = {},
        onPasswordChange = {},
        onLoginClick = {},
        onRegisterClick = {},
        onForgotPasswordClick = {}
    )
}