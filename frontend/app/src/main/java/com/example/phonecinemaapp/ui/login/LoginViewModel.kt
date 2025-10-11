package com.example.phonecinemaapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val contrasena: String = "",
    val emailError: String? = null,
    val passError: String? = null,
    val canSubmit: Boolean = false,
    val isSubmitting: Boolean = false,
    val loginExitoso: Boolean = false,
    val errorMsg: String? = null
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginChange(email: String, contrasena: String) {
        val emailError = if (email.isBlank()) "El email es obligatorio" else null
        val passError = if (contrasena.isBlank()) "La contraseña es obligatoria" else null
        val canSubmit = emailError == null && passError == null

        _uiState.value = _uiState.value.copy(
            email = email,
            contrasena = contrasena,
            emailError = emailError,
            passError = passError,
            canSubmit = canSubmit,
            errorMsg = null // limpiar error global al escribir
        )
    }

    fun iniciarSesion() {
        val state = _uiState.value
        if (!state.canSubmit) return

        viewModelScope.launch {
            _uiState.value = state.copy(isSubmitting = true, errorMsg = null)

            // Simulación de login (reemplazar con API real)
            kotlinx.coroutines.delay(1000)

            if (state.email == "demo@correo.com" && state.contrasena == "123456") {
                _uiState.value = _uiState.value.copy(
                    loginExitoso = true,
                    isSubmitting = false
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    loginExitoso = false,
                    isSubmitting = false,
                    errorMsg = "Credenciales incorrectas"
                )
            }
        }
    }

    fun clearLoginResult() {
        _uiState.value = _uiState.value.copy(loginExitoso = false, errorMsg = null)
    }
}
