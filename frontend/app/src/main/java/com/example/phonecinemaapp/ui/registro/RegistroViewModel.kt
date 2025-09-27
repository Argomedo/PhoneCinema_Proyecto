package com.example.phonecinemaapp.ui.registro

// IMPORTS NECESARIOS
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val contrasena: String = "",
    val confirmarContrasena: String = "",
    val registroExitoso: Boolean = false,
    val errorMensaje: String? = null
)

class RegistroViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    // Dentro de la clase RegistroViewModel

    fun onRegistroChange(nombre: String, email: String, contrasena: String, confirmar: String) {
        _uiState.value = _uiState.value.copy(
            nombre = nombre,
            email = email,
            contrasena = contrasena,
            confirmarContrasena = confirmar // <-- AÑADIDO
        )
    }

    fun registrarUsuario() {
        // Validaciones que ya teníamos...
        if (uiState.value.nombre.isBlank() || uiState.value.email.isBlank() || uiState.value.contrasena.isBlank()) {
            _uiState.value =
                _uiState.value.copy(errorMensaje = "Todos los campos son obligatorios.")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(uiState.value.email).matches()) {
            _uiState.value =
                _uiState.value.copy(errorMensaje = "El formato del email no es válido.")
            return
        }
        if (uiState.value.contrasena.length < 6) {
            _uiState.value =
                _uiState.value.copy(errorMensaje = "La contraseña debe tener al menos 6 caracteres.")
            return
        }

        // NUEVA VALIDACIÓN: Las contraseñas deben coincidir
        if (uiState.value.contrasena != uiState.value.confirmarContrasena) {
            _uiState.value = _uiState.value.copy(errorMensaje = "Las contraseñas no coinciden.")
            return
        }

        _uiState.value = _uiState.value.copy(errorMensaje = null)

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(registroExitoso = true)
        }
    }
}