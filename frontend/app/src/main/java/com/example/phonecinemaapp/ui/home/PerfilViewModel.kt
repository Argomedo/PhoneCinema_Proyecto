package com.example.phonecinemaapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.local.user.UserEntity
import com.example.phonecinemaapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PerfilUiState(
    val id: Long = 0L,
    val nombre: String = "",
    val email: String = "",
    val fotoUrl: String = "",
    val isLoggedOut: Boolean = false,
    val errorMensaje: String? = null
)

class PerfilViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    fun cargarUsuario(email: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)
            if (user != null) {
                _uiState.update {
                    it.copy(
                        id = user.id,
                        nombre = user.name,
                        email = user.email
                    )
                }
            } else {
                _uiState.update { it.copy(errorMensaje = "Usuario no encontrado") }
            }
        }
    }

    fun onNombreChange(newNombre: String) {
        _uiState.update { it.copy(nombre = newNombre) }
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun guardarCambios() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.id != 0L) {
                val updated = UserEntity(
                    id = state.id,
                    name = state.nombre,
                    email = state.email,
                    password = "" // no se actualiza desde aquí
                )
                userRepository.updateUser(updated)
            }
        }
    }

    fun logout() {
        _uiState.update { it.copy(isLoggedOut = true) }
    }
}
