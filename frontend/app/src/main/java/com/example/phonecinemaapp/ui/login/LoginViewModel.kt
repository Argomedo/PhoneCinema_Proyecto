package com.example.phonecinemaapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.local.user.UserEntity
import com.example.phonecinemaapp.data.repository.UserRepository
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
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var currentUser: UserEntity? = null
        private set

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

        viewModelScope.launch {
            val result = userRepository.login(state.email, state.contrasena)
            if (result.isSuccess) {
                currentUser = result.getOrNull()
                _uiState.update { it.copy(loginExitoso = true, errorMsg = null) }
            } else {
                _uiState.update {
                    it.copy(errorMsg = result.exceptionOrNull()?.message ?: "Error al iniciar sesión")
                }
            }
        }
    }

    fun clearLoginResult() {
        _uiState.update { it.copy(loginExitoso = false, errorMsg = null) }
    }
}
