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
    val contrasena: String = "",
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
        _uiState.update { it.copy(contrasena = password, errorMsg = null) }
    }

    fun iniciarSesion() {
        val state = _uiState.value

        if (state.email.isBlank() || state.contrasena.isBlank()) {
            _uiState.update { it.copy(errorMsg = "Debes completar todos los campos") }
            return
        }

        // Login directo sin backend
        if (state.email == "admin@phonecinema.com" && state.contrasena == "Admin123!") {

            UserSession.currentUser = UserEntity(
                id = 1,
                name = "Administrador",
                email = state.email,
                password = state.contrasena,
                photousuario = "",
                role = "Admin"
            )

            _uiState.update { it.copy(loginExitoso = true) }
            return
        }

        // Si no coincide, marcar error inmediato
        _uiState.update { it.copy(errorMsg = "Credenciales incorrectas") }
    }


    fun clearLoginResult() {
        _uiState.update { it.copy(loginExitoso = false, errorMsg = null) }
    }
}
