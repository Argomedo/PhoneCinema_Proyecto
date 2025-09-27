package com.example.phonecinemaapp.ui.login // Paquete corregido

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonecinemaapp.R

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
            value = uiState.email,
            onValueChange = { loginViewModel.onLoginChange(it, uiState.contrasena) },
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
            value = uiState.contrasena,
            onValueChange = { loginViewModel.onLoginChange(uiState.email, it) },
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
            TextButton(onClick = { /* TODO */ }) {
                Text("¿Olvidaste tu Contraseña?", color = Color.White, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { loginViewModel.iniciarSesion() },
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

        TextButton(onClick = onNavigateToRegistro) {
            Text("¿No tienes una cuenta? Regístrate aquí", color = Color.White)
        }
        Spacer(modifier = Modifier.height(80.dp))
    }
}