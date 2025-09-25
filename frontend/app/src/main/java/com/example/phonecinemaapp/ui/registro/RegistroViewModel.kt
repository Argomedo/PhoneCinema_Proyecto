package com.phonecinema.app.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// 1. Definimos el "Estado": una clase que contiene todos los datos
// que necesita la pantalla para dibujarse.
data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val contrasena: String = "",
    val registroExitoso: Boolean = false,
    val error: String? = null
)

// 2. Creamos el ViewModel
class RegistroViewModel : ViewModel() {

    // Contenedor privado del estado. Solo el ViewModel puede modificarlo.
    private val _uiState = MutableStateFlow(RegistroUiState())
    // Versión pública y de solo lectura del estado para que la UI la observe.
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    // 3. Definimos las Acciones (eventos) que la UI puede llamar

    // Esta función se llamará cada vez que el usuario escriba en un campo de texto.
    fun onRegistroChange(nombre: String, email: String, contrasena: String) {
        _uiState.value = _uiState.value.copy(
            nombre = nombre,
            email = email,
            contrasena = contrasena
        )
    }

    // Esta función se llamará cuando se presione el botón de registrar.
    fun registrarUsuario() {
        viewModelScope.launch {
            // Por ahora, solo simulamos un registro exitoso.
            // Más adelante, aquí llamaremos a nuestro microservicio.
            _uiState.value = _uiState.value.copy(registroExitoso = true)
        }
    }
}