package com.example.phonecinemaapp.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val registroExitoso: Boolean = false,
    val errorMensaje: String? = null
)

class RegistroViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun onRegistroChange(nombre: String, email: String, contrasena: String, confirmar: String) {
        _uiState.value = _uiState.value.copy(
            nombre = nombre,
            email = email,
            contrasena = contrasena,
            confirmarContrasena = confirmar
        )
    }

    fun registrarUsuario() {
        val state = uiState.value

        if (state.nombre.isBlank() || state.email.isBlank() || state.contrasena.isBlank()) {
            _uiState.value = state.copy(errorMensaje = "Todos los campos son obligatorios.")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            _uiState.value = state.copy(errorMensaje = "Formato de email inválido.")
            return
        }
        if (state.contrasena.length < 6) {
            _uiState.value = state.copy(errorMensaje = "La contraseña debe tener al menos 6 caracteres.")
            return
        }
        if (state.contrasena != state.confirmarContrasena) {
            _uiState.value = state.copy(errorMensaje = "Las contraseñas no coinciden.")
            return
        }

        _uiState.value = state.copy(errorMensaje = null)

        viewModelScope.launch {
            _uiState.value = state.copy(registroExitoso = true)
        }
    }
}
