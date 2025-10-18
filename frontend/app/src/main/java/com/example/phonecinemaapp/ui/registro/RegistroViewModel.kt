package com.example.phonecinemaapp.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val registroExitoso: Boolean = false,
    val errorMensaje: String? = null
)

class RegistroViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) = _uiState.update { it.copy(nombre = value) }
    fun onEmailChange(value: String) = _uiState.update { it.copy(email = value) }
    fun onPasswordChange(value: String) = _uiState.update { it.copy(contrasena = value) }
    fun onConfirmPasswordChange(value: String) = _uiState.update { it.copy(confirmarContrasena = value) }

    fun registrarUsuario() {
        val s = _uiState.value
        if (s.contrasena != s.confirmarContrasena) {
            _uiState.update { it.copy(errorMensaje = "Las contraseñas no coinciden") }
            return
        }
        if (s.nombre.isBlank() || s.email.isBlank() || s.contrasena.isBlank()) {
            _uiState.update { it.copy(errorMensaje = "Todos los campos son obligatorios") }
            return
        }

        viewModelScope.launch {
            val result = userRepository.register(s.nombre, s.email, s.contrasena)
            _uiState.update {
                if (result.isSuccess) it.copy(registroExitoso = true, errorMensaje = null)
                else it.copy(errorMensaje = result.exceptionOrNull()?.message)
            }
        }
    }
}
