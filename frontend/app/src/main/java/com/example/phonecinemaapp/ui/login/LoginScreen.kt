package com.example.phonecinemaapp.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.phonecinemaapp.R

// ------------------- Stateful Composable -------------------

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel(),
    onLoginExitoso: () -> Unit,
    onNavigateToRegistro: () -> Unit
) {
    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.loginExitoso) {
        if (uiState.loginExitoso) {
            Toast.makeText(context, "¡Login exitoso!", Toast.LENGTH_SHORT).show()
            loginViewModel.clearLoginResult()
            onLoginExitoso()
        }
    }

    LoginScreenContent(
        emailState = uiState.email,
        passwordState = uiState.contrasena,
        emailError = uiState.emailError,
        passError = uiState.passError,
        canSubmit = uiState.canSubmit,
        isSubmitting = uiState.isSubmitting,
        errorMsg = uiState.errorMsg,
        onEmailChange = { email -> loginViewModel.onLoginChange(email, uiState.contrasena) },
        onPasswordChange = { pass -> loginViewModel.onLoginChange(uiState.email, pass) },
        onLoginClick = { loginViewModel.iniciarSesion() },
        onRegisterClick = onNavigateToRegistro,
        onForgotPasswordClick = { /* TODO */ }
    )
}

// ------------------- Stateless UI Composable -------------------

@Composable
fun LoginScreenContent(
    emailState: String,
    passwordState: String,
    emailError: String?,
    passError: String?,
    canSubmit: Boolean,
    isSubmitting: Boolean,
    errorMsg: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

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
            isError = emailError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray
            )
        )
        if (emailError != null) {
            Text(emailError, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordState,
            onValueChange = onPasswordChange,
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            isError = passError != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.LightGray,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.LightGray
            )
        )
        if (passError != null) {
            Text(passError, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        }

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
            enabled = canSubmit && !isSubmitting,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(50),
        ) {
            if (isSubmitting) {
                CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Validando...")
            } else {
                Text("ENTRAR", fontSize = 16.sp)
            }
        }

        if (errorMsg != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMsg, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onRegisterClick) {
            Text("¿No tienes una cuenta? Regístrate aquí", color = Color.White)
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
