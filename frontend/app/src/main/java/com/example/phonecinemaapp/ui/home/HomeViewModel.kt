package com.example.phonecinemaapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.remote.dto.PeliculaDTO
import com.example.phonecinemaapp.data.repository.PeliculasRepositoryRemote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Categoria(
    val nombre: String,
    val peliculas: List<PeliculaDTO>
)

data class HomeUiState(
    val categorias: List<Categoria> = emptyList(),
    val nombreUsuario: String = "Invitado",
    val loading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val repository: PeliculasRepositoryRemote
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarPeliculas()
    }

    fun loadUser(nombre: String) {
        _uiState.value = _uiState.value.copy(nombreUsuario = nombre)
    }

    private fun cargarPeliculas() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true)

                val peliculas: List<PeliculaDTO> = repository.getAll()

                val categorias = peliculas
                    .groupBy { it.genero }
                    .map { (genero, lista) ->
                        Categoria(
                            nombre = genero,
                            peliculas = lista
                        )
                    }

                _uiState.value = HomeUiState(
                    categorias = categorias,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = e.message ?: "Error al cargar películas"
                )
            }
        }
    }
}
