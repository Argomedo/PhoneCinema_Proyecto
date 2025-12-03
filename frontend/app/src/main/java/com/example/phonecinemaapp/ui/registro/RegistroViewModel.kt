package com.example.phonecinemaapp.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.repository.UserRepository
import com.example.phonecinemaapp.domain.validation.validateEmail
import com.example.phonecinemaapp.domain.validation.validateName
import com.example.phonecinemaapp.domain.validation.validatePassword
import com.example.phonecinemaapp.domain.validation.validatePasswordConfirm
import com.example.phonecinemaapp.domain.validation.validateRegisterFields
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

    // Validación específica por orden de campo
    fun onNameChange(value: String) {
        _uiState.update {
            it.copy(
                nombre = value,
                errorMensaje = validateName(value)
            )
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update {
            it.copy(
                email = value,
                errorMensaje = validateEmail(value)
            )
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                contrasena = value,
                errorMensaje = validatePassword(value)
            )
        }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update {
            it.copy(
                confirmarContrasena = value,
                errorMensaje = validatePasswordConfirm(it.contrasena, value)
            )
        }
    }

    // Validación global solo al presionar "Registrar"
    fun registrarUsuario() {
        val s = _uiState.value
        val error = validateRegisterFields(s.nombre, s.email, s.contrasena, s.confirmarContrasena)

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
