package com.example.phonecinemaapp.ui.perfil

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinema.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.data.session.UserSession
import com.example.phonecinemaapp.data.local.UserPrefs
import com.example.phonecinemaapp.data.local.user.UserEntity
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
    val userReviews: List<Any> = emptyList()
)

class PerfilViewModel(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    private var currentUser = UserSession.currentUser

    init {
        val user = UserSession.currentUser
        if (user != null) {
            _uiState.update {
                it.copy(
                    id = user.id,
                    nombre = user.nombre,
                    email = user.email,
                    fotoUri = user.fotoPerfilUrl ?: ""
                )
            }
            currentUser = user
        }
    }

    fun cargarUsuario(id: Long) {
        viewModelScope.launch {
            try {
                val user = userRepository.getUserById(id)

                val entity = UserEntity(
                    id = user.id,
                    nombre = user.nombre,
                    email = user.email,
                    fotoPerfilUrl = user.fotoPerfilUrl ?: "",
                    rol = user.rol.nombre
                )

                currentUser = entity
                UserSession.currentUser = entity

                _uiState.update {
                    it.copy(
                        id = entity.id,
                        nombre = entity.nombre,
                        email = entity.email,
                        fotoUri = entity.fotoPerfilUrl
                    )
                }

            } catch (_: Exception) {
                _uiState.update { it.copy(errorMensaje = "No fue posible cargar el usuario") }
            }
        }
    }


    fun cargarResenasUsuario(userId: Long) {
        _uiState.update { it.copy(userReviews = emptyList()) }
    }

    // ------------------------
    // FORMULARIO / CAMPOS
    // ------------------------
    fun onNombreChange(nuevo: String) {
        _uiState.update { it.copy(nombre = nuevo) }
    }

    fun onEmailChange(nuevo: String) {
        _uiState.update { it.copy(email = nuevo) }
    }

    fun onFotoChange(nuevo: String) {
        _uiState.update { it.copy(fotoUri = nuevo) }

        // persistencia instantánea si el usuario ya existe
        if (currentUser != null) {
            currentUser = currentUser!!.copy(fotoPerfilUrl = nuevo)
            UserSession.currentUser = currentUser
        }
    }

    fun guardarCambios(context: Context) {
        val usuarioActual = currentUser ?: run {
            _uiState.update { it.copy(errorMensaje = "No hay usuario cargado") }
            return
        }

        viewModelScope.launch {
            try {
                userRepository.actualizarFotoUsuario(
                    userId = usuarioActual.id,
                    fotoUrl = _uiState.value.fotoUri
                )

                val actualizado = usuarioActual.copy(
                    fotoPerfilUrl = _uiState.value.fotoUri
                )

                UserSession.currentUser = actualizado
                currentUser = actualizado

                _uiState.update {
                    it.copy(successMensaje = "Foto actualizada correctamente")
                }

            } catch (_: Exception) {
                _uiState.update {
                    it.copy(errorMensaje = "No se pudo guardar la foto")
                }
            }
        }
    }



    // ------------------------
    // CAMBIAR CONTRASEÑA
    // ------------------------
    fun cambiarPassword(passwordActual: String, passwordNueva: String) {
        viewModelScope.launch {
            try {
                val userId = _uiState.value.id.toInt()

                userRepository.cambiarPassword(
                    userId = userId,
                    actual = passwordActual,
                    nueva = passwordNueva
                )

                _uiState.update {
                    it.copy(successMensaje = "Contraseña actualizada correctamente")
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(errorMensaje = "No se pudo cambiar la contraseña")
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMensaje = null, successMensaje = null) }
    }

    fun logout() {
        _uiState.update { it.copy(isLoggedOut = true) }
    }
}