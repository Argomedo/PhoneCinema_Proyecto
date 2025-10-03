package com.example.phonecinemaapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.phonecinemaapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// --- MODELOS DE DATOS ---
data class Pelicula(val id: Int,
                    val nombre: String,
                    val posterResId: Int,
                    val descripcion: String = "",
                    val genero: String = "",
                    val duracion: String = "",
                    val año: Int = 0)
data class Categoria(val nombre: String,
                     val peliculas: List<Pelicula>)

// --- ESTADO DE LA UI ---
data class HomeUiState(
    val categorias: List<Categoria> = emptyList(),
    val nombreUsuario: String = "Invitado"
)

// --- VIEWMODEL ---
class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarDatosDeEjemplo()
    }

    private fun cargarDatosDeEjemplo() {
        val peliculasDeAccion = listOf(
            Pelicula(1,
                "Joker",
                R.drawable.cartelera_joker,
                "Un comediante fallido se vuelve loco y se transforma en un criminal psicópata.",
                "Drama",
                "2h 2m",
                2019
                ),
            Pelicula(2,
                "Kill Bill",
                R.drawable.cartelera_killbill,
                "Una asesina se despierta de un coma y busca venganza contra su antiguo jefe.",
                "Accion",
                "1h 51m",
                2003)
        )
        val peliculasDeComedia = listOf(
            Pelicula(3,
                "Lost in Translation",
                R.drawable.cartelera_lost,
                "Una amistad inusual se forma entre dos americanos en Tokio.",
                "Comedia/Drama",
                "1h 42m",
                2003)
        )

        _uiState.value = HomeUiState(
            categorias = listOf(
                Categoria("Acción", peliculasDeAccion),
                Categoria("Comedia", peliculasDeComedia)
            ),
            nombreUsuario = "Diego"
        )
    }
}
