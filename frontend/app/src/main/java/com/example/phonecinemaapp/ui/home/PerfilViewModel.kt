package com.example.phonecinemaapp.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinema.data.repository.ReviewRepository
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
    val userReviews: List<Any> = emptyList()
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
            try {
                val user = userRepository.getUserById(id)

                _uiState.update {
                    it.copy(
                        id = user.id,
                        nombre = user.nombre,
                        email = user.email,
                        fotoUri = user.fotoPerfilUrl ?: ""
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

    fun onNombreChange(nuevo: String) {
        _uiState.update { it.copy(nombre = nuevo) }
    }

    fun onEmailChange(nuevo: String) {
        _uiState.update { it.copy(email = nuevo) }
    }

    fun onFotoChange(nuevo: String) {
        _uiState.update { it.copy(fotoUri = nuevo) }
    }

    fun guardarCambios() {
        val usuarioActual = currentUser ?: run {
            _uiState.update { it.copy(errorMensaje = "No hay usuario cargado") }
            return
        }

        val actualizado = usuarioActual.copy(
            nombre = _uiState.value.nombre,
            email = _uiState.value.email,
            fotoPerfilUrl = _uiState.value.fotoUri
        )

        UserSession.currentUser = actualizado
        currentUser = actualizado

        _uiState.update {
            it.copy(successMensaje = "Datos actualizados localmente")
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(errorMensaje = null, successMensaje = null) }
    }

    fun logout() {
        _uiState.update { it.copy(isLoggedOut = true) }
    }
}
