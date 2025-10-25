package com.example.phonecinemaapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.local.user.UserEntity
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.data.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PerfilUiState(
    val id: Long = 0L,
    val nombre: String = "",
    val email: String = "",
    val fotoUri: String = "",
    val isLoggedOut: Boolean = false,
    val errorMensaje: String? = null,
    val successMensaje: String? = null
)

class PerfilViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    private var currentUser: UserEntity? = null

    fun cargarUsuario(email: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)
            if (user != null) {
                currentUser = user
                _uiState.update {
                    it.copy(
                        id = user.id,
                        nombre = user.name,
                        email = user.email,
                        fotoUri = user.photousuario
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
            val usuarioActual = currentUser
            if (usuarioActual != null) {
                val actualizado = usuarioActual.copy(
                    name = _uiState.value.nombre,
                    email = _uiState.value.email,
                    photousuario = _uiState.value.fotoUri
                )

                userRepository.updateUser(actualizado)

                // Actualiza la sesión global para reflejar los nuevos datos
                UserSession.currentUser = actualizado

                currentUser = actualizado
                _uiState.update {
                    it.copy(
                        successMensaje = "Datos guardados correctamente",
                        errorMensaje = null
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        errorMensaje = "No hay usuario cargado",
                        successMensaje = null
                    )
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update {
            it.copy(errorMensaje = null, successMensaje = null)
        }
    }

    fun logout() {
        _uiState.update { it.copy(isLoggedOut = true) }
    }
}
