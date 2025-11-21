package com.example.phonecinemaapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinema.data.dto.UserDto
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinema.data.repository.ReviewRepository   // <- este es el remote
import com.example.phonecinemaapp.data.local.user.toDto
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
    val successMensaje: String? = null,
    val userReviews: List<Any> = emptyList() // por ahora vacío
)

class PerfilViewModel(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    private var currentUser = UserSession.currentUser

    fun cargarUsuario(id: Long) {
        viewModelScope.launch {
            val user = userRepository.getUserById(id)

            _uiState.update {
                it.copy(
                    id = user.id,
                    nombre = user.username,
                    email = "",
                    fotoUri = ""
                )
            }
        }
    }





    // No existe endpoint para traer reseñas por usuario en tu microservicio.
    // Se deja vacía para evitar errores.
    fun cargarResenasUsuario(userEmail: Long) {
        _uiState.update { it.copy(userReviews = emptyList()) }
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

    fun cambiarPassword(nuevaPassword: String, confirmacion: String) {
        viewModelScope.launch {

            val usuarioActual = currentUser ?: run {
                _uiState.update { it.copy(errorMensaje = "No hay usuario cargado") }
                return@launch
            }

            if (nuevaPassword != confirmacion) {
                _uiState.update { it.copy(errorMensaje = "Las contraseñas no coinciden") }
                return@launch
            }

            if (nuevaPassword.length < 6) {
                _uiState.update { it.copy(errorMensaje = "La contraseña debe tener al menos 6 caracteres") }
                return@launch
            }

            val actualizadoEntity = usuarioActual.copy(password = nuevaPassword)
            val dto = actualizadoEntity.toDto()

            userRepository.updateUser(dto)

            UserSession.currentUser = actualizadoEntity
            currentUser = actualizadoEntity

            _uiState.update {
                it.copy(
                    successMensaje = "Contraseña actualizada correctamente",
                    errorMensaje = null
                )
            }
        }
    }



    fun guardarCambios() {
        viewModelScope.launch {
            val usuarioActual = currentUser
            if (usuarioActual != null) {

                val actualizadoEntity = usuarioActual.copy(
                    name = _uiState.value.nombre,
                    email = _uiState.value.email,
                    photousuario = _uiState.value.fotoUri
                )

                // Convertir a DTO para backend
                val dto = actualizadoEntity.toDto()

                userRepository.updateUser(dto)

                UserSession.currentUser = actualizadoEntity
                currentUser = actualizadoEntity

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
