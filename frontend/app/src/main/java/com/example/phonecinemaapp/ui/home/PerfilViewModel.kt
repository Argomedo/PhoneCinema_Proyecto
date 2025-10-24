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

// NUEVO: Estado actualizado con campos para foto y mensajes
data class PerfilUiState(
    val id: Long = 0L,
    val nombre: String = "",
    val email: String = "",
    val fotoUri: String = "", // NUEVO: para almacenar la URI de la foto
    val isLoggedOut: Boolean = false,
    val errorMensaje: String? = null,
    val successMensaje: String? = null // NUEVO: para mensajes de éxito
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
                        email = user.email,
                        fotoUri = user.photousuario // NUEVO: cargar la foto existente del usuario
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

    // NUEVO: Función para actualizar la foto del perfil
    fun onFotoChange(newFotoUri: String) {
        _uiState.update {
            it.copy(
                fotoUri = newFotoUri,
                successMensaje = "Foto actualizada correctamente"
            )
        }
    }

    fun guardarCambios() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.id != 0L) {
                val existingUser = userRepository.getUserByEmail(state.email)
                val passwordToKeep = existingUser?.password ?: ""

                // NUEVO: Incluir la foto al actualizar el usuario
                val updated = UserEntity(
                    id = state.id,
                    name = state.nombre,
                    email = state.email,
                    password = passwordToKeep,
                    photousuario = state.fotoUri // NUEVO: guardar la URI de la foto
                )
                userRepository.updateUser(updated)
                _uiState.update {
                    it.copy(
                        successMensaje = "Datos guardados correctamente",
                        errorMensaje = null
                    )
                }
            }
        }
    }

    // NUEVO: Función para limpiar mensajes de error y éxito
    fun clearMessages() {
        _uiState.update {
            it.copy(
                errorMensaje = null,
                successMensaje = null
            )
        }
    }

    fun logout() {
        _uiState.update { it.copy(isLoggedOut = true) }
    }
}
