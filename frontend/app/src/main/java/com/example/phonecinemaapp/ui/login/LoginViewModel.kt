package com.example.phonecinemaapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.local.user.UserEntity
import com.example.phonecinemaapp.data.session.UserSession
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loginExitoso: Boolean = false,
    val errorMsg: String? = null
)

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorMsg = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMsg = null) }
    }

    fun iniciarSesion() {
        val state = _uiState.value

        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.update { it.copy(errorMsg = "Debes completar todos los campos") }
            return
        }

        viewModelScope.launch {
            try {
                val response = authRepository.login(state.email, state.password)

                val user = UserEntity(
                    id = response.id ?: 0L,
                    nombre = response.nombre ?: "",
                    email = response.email ?: "",
                    fotoPerfilUrl = response.fotoPerfilUrl ?: "",
                    rol = response.rol ?: "Usuario"
                )

                UserSession.setUser(user)
                userRepository.saveUser(user)

                _uiState.update { it.copy(loginExitoso = true) }

            } catch (_: Exception) {
                _uiState.update { it.copy(errorMsg = "Credenciales incorrectas") }
            }
        }
    }

    fun clearLoginResult() {
        _uiState.update { it.copy(loginExitoso = false, errorMsg = null) }
    }
}
