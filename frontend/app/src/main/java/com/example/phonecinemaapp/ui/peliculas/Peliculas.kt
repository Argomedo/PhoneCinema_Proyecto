package com.example.phonecinemaapp.data

import com.example.phonecinemaapp.R
import com.example.phonecinemaapp.ui.home.Pelicula

object PeliculaRepository {

    private val peliculas = listOf(
        // Acción
        Pelicula(1, "Joker", R.drawable.cartelera_joker, "Un comediante fallido se vuelve loco y se transforma en un criminal psicópata.", "Acción", "2h 2m", 2019),
        Pelicula(2, "Kill Bill", R.drawable.cartelera_killbill, "Una asesina se despierta de un coma y busca venganza contra su antiguo jefe.", "Acción", "1h 51m", 2003),
        Pelicula(3, "Matrix", R.drawable.cartelera_matrix, "Un experto en computadoras descubre que su mundo es una simulación total.", "Acción", "2h 16m", 1999),
        Pelicula(4, "John Wick", R.drawable.cartelera_jhonwick, "Un ex asesino a sueldo regresa al inframundo criminal.", "Acción", "1h 41m", 2014),

        // Comedia
        Pelicula(5, "Loco por Mary", R.drawable.cartelera_locopormary, "Un hombre emplea a un detective privado para espiar a la mujer de la que está enamorado.", "Comedia", "1h 42m", 2003),
        Pelicula(6, "Irene, yo y mi otro yo", R.drawable.cartelera_memyself, "Charlie es un policía con doble personalidad que se enamora de la misma mujer.", "Comedia", "1h 56m", 2000),
        Pelicula(7, "Scary Movie", R.drawable.cartelera_scarymovie, "Parodia de los filmes de asesinatos donde un homicida acecha adolescentes.", "Comedia", "1h 28m", 2000),
        Pelicula(8, "Supercool", R.drawable.cartelera_supercool, "Dos estudiantes buscan disfrutar su fiesta de graduación.", "Comedia", "1h 59m", 2007),

        // Romance
        Pelicula(9, "Lost in Translation", R.drawable.cartelera_lost, "Una amistad inusual se forma entre dos americanos en Tokio.", "Romance", "1h 42m", 2003),
        Pelicula(10, "Cuestión de Tiempo", R.drawable.cartelera_cuestion, "Tim Lake puede viajar en el tiempo para conquistar a Mary.", "Romance", "2h 3m", 2013),
        Pelicula(11, "500 días con ella", R.drawable.cartelera_500diasconella, "Tom recuerda los 500 días con Summer para entender su relación.", "Romance", "1h 42m", 2009),
        Pelicula(12, "Orgullo y Prejuicio", R.drawable.cartelera_orgulloyprejuicio, "Elizabeth Bennet conoce al apuesto Sr. Darcy.", "Romance", "2h 8m", 2005),

        // Drama
        Pelicula(13, "Eterno Resplandor de una mente sin recuerdos", R.drawable.cartelera_eternal, "Una pareja borra sus recuerdos tras una ruptura dolorosa.", "Drama", "1h 48m", 2004),
        Pelicula(14, "Requiem por un sueño", R.drawable.cartelera_requiemforadream, "Una viuda se vuelve adicta mientras su hijo enfrenta su propia adicción.", "Drama", "1h 42m", 2000),
        Pelicula(15, "American History X", R.drawable.cartelera_historiamaericana, "Exneonazi intenta evitar que su hermano repita sus errores.", "Drama", "1h 59m", 1998),
        Pelicula(16, "Sueños de Fuga", R.drawable.cartelera_shawshank, "Un hombre inocente es enviado a una prisión corrupta y sentenciado por doble asesinato.", "Drama", "2h 2m", 1994)
    )

    fun getByCategoria(categoria: String): List<Pelicula> {
        return when (categoria) {
            "Acción" -> peliculas.filter { it.id in 1..4 }
            "Comedia" -> peliculas.filter { it.id in 5..8 }
            "Romance" -> peliculas.filter { it.id in 9..12 }
            "Drama" -> peliculas.filter { it.id in 13..16 }
            else -> emptyList()
        }
    }

    fun getById(id: Int): Pelicula? = peliculas.find { it.id == id }
}