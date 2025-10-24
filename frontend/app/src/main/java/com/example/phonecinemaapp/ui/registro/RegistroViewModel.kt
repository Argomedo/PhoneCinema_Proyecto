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

    // --- Actualización de campos con validación inmediata ---
    fun onNameChange(value: String) {
        _uiState.update { it.copy(nombre = value, errorMensaje = validarCampos(it.copy(nombre = value))) }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, errorMensaje = validarCampos(it.copy(email = value))) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(contrasena = value, errorMensaje = validarCampos(it.copy(contrasena = value))) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmarContrasena = value, errorMensaje = validarCampos(it.copy(confirmarContrasena = value))) }
    }

    // --- Validación completa de los campos ---
    private fun validarCampos(s: RegistroUiState): String? {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        return when {
            s.nombre.length < 3 -> "El nombre debe tener al menos 3 caracteres"
            !emailRegex.matches(s.email) -> "Ingrese un correo válido"
            s.contrasena.length < 6 -> "La contraseña debe tener mínimo 6 caracteres"
            s.contrasena != s.confirmarContrasena -> "Las contraseñas no coinciden"
            else -> null
        }
    }

    fun registrarUsuario() {
        val s = _uiState.value
        val error = validarCampos(s)

        if (error != null) {
            _uiState.update { it.copy(errorMensaje = error) }
            return
        }

        viewModelScope.launch {
            val result = userRepository.register(s.nombre.trim(), s.email.trim(), s.contrasena)
            _uiState.update {
                if (result.isSuccess)
                    it.copy(registroExitoso = true, errorMensaje = null)
                else
                    it.copy(errorMensaje = result.exceptionOrNull()?.message ?: "Error al registrar usuario")
            }
        }
    }
}
