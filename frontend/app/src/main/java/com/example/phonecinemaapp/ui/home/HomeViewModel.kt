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
                R.drawable.cartelera_joker
                ),
            Pelicula(2,
                "Kill Bill",
                R.drawable.cartelera_killbill,
                "Una asesina se despierta de un coma y busca venganza contra su antiguo jefe.",
                "Acción",
                "1h 51m",
                2003
            ),
            Pelicula(3,
                "Matrix",
                R.drawable.cartelera_matrix,
                "Un experto en computadoras descubre que su mundo es una simulación total creada con maliciosas intenciones por parte de la ciberinteligencia.",
                "Acción",
                "2h 16m",
                1999
            ),
            Pelicula(4,
                "John Wick",
                R.drawable.cartelera_jhonwick,
                "Un ex asesino a sueldo que se ve obligado a regresar al inframundo criminal que había abandonado anteriormente.",
                "Acción",
                "1h 41m",
                2014
            )
        )

        val peliculasDeComedia = listOf(
            Pelicula(5,
                "Loco por Mary",
                R.drawable.cartelera_locopormary,
                "Un hombre emplea a un detective privado de mala fama para espiar a la mujer de la que ha estado enamorado desde la escuela..",
                "Comedia",
                "1h 42m",
                2003
            ),
            Pelicula(6,
                "Irene, yo y mi otro yo",
                R.drawable.cartelera_memyself,
                "Charlie es un policía experimentado con doble personalidad: una gentil y otra indeseable que emerge cuando no toma su medicación. Sus dos yos se enamoran de la misma mujer, a quien debe escoltar desde Rhode Island hasta Nueva York.",
                "Comedia",
                "1h 56m",
                2000
            ),
            Pelicula(7,
                "Scary Movie",
                R.drawable.cartelera_scarymovie,
                "Una parodia de los filmes de asesinatos donde un homicida vengativo acecha a un grupo de adolescentes.",
                "Comedia",
                "1h 28m",
                2000
            ),
            Pelicula(8,
                "Supercool",
                R.drawable.cartelera_supercool,
                "La ansiedad por la separación representa un problema para dos jóvenes estudiantes de último año de preparatoria, que esperan divertirse y conseguir chicas hermosas en su fiesta de graduación.",
                "Comedia",
                "1h 59m",
                2007
            )
        )

        val peliculasDeRomance = listOf(
            Pelicula(9,
                "Lost in Translation",
                R.drawable.cartelera_lost,
                "Una amistad inusual se forma entre dos americanos en Tokio.",
                "Comedia/Drama",
                "1h 42m",
                2003
            ),
            Pelicula(10,
                "Cuestión de Tiempo",
                R.drawable.cartelera_cuestion,
                "Tim Lake descubre que puede viajar en el tiempo y usa ese poder para conquistar a Mary. Con el tiempo comprende que, aunque puede alterar momentos, no puede evitar las dificultades normales de la vida.",
                "Comedia/Drama",
                "2h 3m",
                2013
            ),
            Pelicula(11,
                "500 días con ella",
                R.drawable.cartelera_500diasconella,
                "Tom recuerda los 500 días con Summer para entender por qué su relación terminó y, al hacerlo, redescubre sus verdaderos intereses y propósito.",
                "Comedia/Drama",
                "1h 42m",
                2009
            ),
            Pelicula(12,
                "Orgullo y Prejuicio",
                R.drawable.cartelera_orgulloyprejuicio,
                "Elizabeth Bennet conoce al apuesto y adinerado Sr. Darcy, con quien, rápidamente, inicia una intensa y compleja relación.",
                "Comedia/Drama",
                "2h 8m",
                2005)
        )

        val peliculasDeDrama = listOf(
            Pelicula(13,
                "Eterno Resplandor de una mente sin recuerdos",
                R.drawable.cartelera_eternal,
                "Parecían la pareja ideal, su primer encuentro fue mágico, pero con el paso del tiempo ella deseó nunca haberlo conocido. Su anhelo se hace realidad gracias a un polémico y radical invento. Sin embargo descubrirán que el destino no se puede controlar.",
                "Drama/Ciencia Ficción",
                "1h 48m",
                2004
            ),
            Pelicula(14,
                "Requiem por un sueño",
                R.drawable.cartelera_requiemforadream,
                "Una envejecida viuda se vuelve adicta a píldoras dietéticas mientras su hijo libra su propia batalla con estupefacientes.",
                "Terror/Drama",
                "1h 42m",
                2000
            ),
            Pelicula(15,
                "American History X",
                R.drawable.cartelera_historiamaericana,
                "Tras ser liberado de la cárcel, un exneonazi trata de evitar que su hermano menor siga sus pasos en la senda del odio.",
                "Drama/Crimen",
                "1h 59m",
                1998
            ),
            Pelicula(16,
                "Sueños de Fuga",
                R.drawable.cartelera_shawshank,
                "En 1947, un hombre inocente es enviado a una corrupta penitenciaría de Maine y sentenciado a dos cadenas perpetuas por un doble asesinato.",
                "Drama/Crimen",
                "2h 2m",
                1994)
        )

        _uiState.value = HomeUiState(
            categorias = listOf(
                Categoria("Acción", peliculasDeAccion),
                Categoria("Comedia", peliculasDeComedia),
                Categoria("Romance", peliculasDeRomance),
                Categoria("Drama", peliculasDeDrama)
            ),
            nombreUsuario = "Invitado"
        )
    }
}
