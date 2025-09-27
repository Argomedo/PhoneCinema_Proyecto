package com.example.phonecinemaapp.ui.login // Paquete corregido

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val contrasena: String = "",
    val loginExitoso: Boolean = false,
    val error: String? = null
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onLoginChange(email: String, contrasena: String) {
        _uiState.value = _uiState.value.copy(email = email, contrasena = contrasena)
    }

    fun iniciarSesion() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(loginExitoso = true)
        }
    }
}