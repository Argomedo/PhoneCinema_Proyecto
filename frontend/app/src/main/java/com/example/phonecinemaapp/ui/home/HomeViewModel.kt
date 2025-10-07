package com.example.phonecinemaapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.phonecinemaapp.data.PeliculaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    init {
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
