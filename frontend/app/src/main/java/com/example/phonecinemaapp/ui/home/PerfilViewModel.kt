package com.example.phonecinemaapp.ui.perfil

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PerfilUiState(
    val nombre: String = "",
    val email: String = "",
    val fotoUrl: String = "", // para preview dinámico
    val isLoggedOut: Boolean = false,
    val errorMensaje: String? = null
)

class PerfilViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        PerfilUiState(
            nombre = "Usuario",
            email = "usuario@email.com",
            fotoUrl = "" // recurso por defecto
        )
    )
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun onNombreChange(newNombre: String) {
        _uiState.value = _uiState.value.copy(nombre = newNombre)
    }

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onFotoChange(newFotoUrl: String) {
        _uiState.value = _uiState.value.copy(fotoUrl = newFotoUrl)
    }

    fun guardarCambios() {
        _uiState.value = _uiState.value.copy(errorMensaje = null)
        // guardar cambios en base de datos o backend
    }

    fun logout() {
        _uiState.value = _uiState.value.copy(isLoggedOut = true)
    }
}
