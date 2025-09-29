package com.example.phonecinemaapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.phonecinemaapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// --- 1. DEFINIMOS LOS MODELOS DE DATOS AQUÍ, EN EL NIVEL SUPERIOR ---
data class Pelicula(val id: Int, val nombre: String, val posterResId: Int)
data class Categoria(val nombre: String, val peliculas: List<Pelicula>)

// --- 2. DEFINIMOS EL ESTADO DE LA UI AQUÍ TAMBIÉN ---
data class HomeUiState(
    val categorias: List<Categoria> = emptyList(),
    val nombreUsuario: String = "Invitado"
)


// --- 3. Y FINALMENTE, LA CLASE VIEWMODEL ---
class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarDatosDeEjemplo()
    }

    private fun cargarDatosDeEjemplo() {
        val peliculasDeAccion = listOf(
            Pelicula(1, "Joker", R.drawable.cartelera_joker),
            Pelicula(2, "Kill Bill", R.drawable.cartelera_killbill)
        )
        val peliculasDeComedia = listOf(
            Pelicula(3, "Lost in Translation", R.drawable.cartelera_lost)
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