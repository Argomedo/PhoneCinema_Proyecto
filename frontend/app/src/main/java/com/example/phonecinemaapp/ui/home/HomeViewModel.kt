package com.example.phonecinemaapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Pelicula(
    val id: Int,
    val nombre: String,
    val posterResId: Int,
    val descripcion: String = "",
    val genero: String = "",
    val duracion: String = "",
    val año: Int = 0
)

data class Categoria(
    val nombre: String,
    val peliculas: List<Pelicula>
)

data class HomeUiState(
    val categorias: List<Categoria> = emptyList(),
    val nombreUsuario: String = "Invitado"
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun loadUser(nombre: String) {
        _uiState.value = _uiState.value.copy(nombreUsuario = nombre)
    }

    init {
        viewModelScope.launch {
            _uiState.value = HomeUiState(
                categorias = listOf(
                    Categoria("Acción", PeliculaRepository.getByCategoria("Acción")),
                    Categoria("Comedia", PeliculaRepository.getByCategoria("Comedia")),
                    Categoria("Romance", PeliculaRepository.getByCategoria("Romance")),
                    Categoria("Drama", PeliculaRepository.getByCategoria("Drama"))
                )
            )
        }
    }
}
