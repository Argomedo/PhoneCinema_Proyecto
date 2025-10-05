package com.example.phonecinemaapp.ui.reseñas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonecinemaapp.R
import com.example.phonecinemaapp.ui.components.AppTopBar
import com.example.phonecinemaapp.ui.home.Pelicula

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    movieId: Int,
    onBackClick: () -> Unit,
    reviewViewModel: ReviewViewModel = viewModel()
) {
    val uiState by reviewViewModel.uiState.collectAsState()

    // Datos de ejemplo de la película basados en el ID
    val pelicula = remember(movieId) {
        when (movieId) {
            1 -> Pelicula(
                id = 1,
                nombre = "Joker",
                posterResId = R.drawable.cartelera_joker,
                descripcion = "Un comediante fallido se vuelve loco y se transforma en un criminal psicópata.",
                genero = "Drama",
                duracion = "2h 2m",
                año = 2019
            )

            2 -> Pelicula(
                id = 2,
                nombre = "Kill Bill",
                posterResId = R.drawable.cartelera_killbill,
                descripcion = "Una asesina se despierta de un coma y busca venganza contra su antiguo jefe.",
                genero = "Acción",
                duracion = "1h 51m",
                año = 2003
            )

            3 -> Pelicula(
                id = 3,
                nombre = "Matrix",
                posterResId = R.drawable.cartelera_matrix,
                descripcion = "Un experto en computadoras descubre que su mundo es una simulación total creada con maliciosas intenciones por parte de la ciberinteligencia.",
                genero = "Acción",
                duracion = "2h 16m",
                año = 1999
            )

            4 -> Pelicula(
                id = 4,
                nombre = "John Wick",
                posterResId = R.drawable.cartelera_jhonwick,
                descripcion = "Un ex asesino a sueldo que se ve obligado a regresar al inframundo criminal que había abandonado anteriormente.",
                genero = "Acción",
                duracion = "1h 41m",
                año = 2014
            )

            5 -> Pelicula(
                id = 5,
                nombre = "Loco por Mary",
                posterResId = R.drawable.cartelera_locopormary,
                descripcion = "Un hombre emplea a un detective privado de mala fama para espiar a la mujer de la que ha estado enamorado desde la escuela.",
                genero = "Comedia",
                duracion = "1h 42m",
                año = 2003
            )

            6 -> Pelicula(
                id = 6,
                nombre = "Irene, yo y mi otro yo",
                posterResId = R.drawable.cartelera_memyself,
                descripcion = "Charlie es un policía con doble personalidad: una gentil y otra indeseable que emerge cuando no toma su medicación. Sus dos yos se enamoran de la misma mujer, a quien debe escoltar desde Rhode Island hasta Nueva York.",
                genero = "Comedia",
                duracion = "1h 56m",
                año = 2000
            )

            7 -> Pelicula(
                id = 7,
                nombre = "Scary Movie",
                posterResId = R.drawable.cartelera_scarymovie,
                descripcion = "Una parodia de los filmes de asesinatos donde un homicida vengativo acecha a un grupo de adolescentes.",
                genero = "Comedia",
                duracion = "1h 28m",
                año = 2000
            )

            8 -> Pelicula(
                id = 8,
                nombre = "Supercool",
                posterResId = R.drawable.cartelera_supercool,
                descripcion = "Dos estudiantes de último año buscan disfrutar su fiesta de graduación y superar su ansiedad por separarse.",
                genero = "Comedia",
                duracion = "1h 59m",
                año = 2007
            )

            9 -> Pelicula(
                id = 9,
                nombre = "Lost in Translation",
                posterResId = R.drawable.cartelera_lost,
                descripcion = "Una amistad inusual se forma entre dos americanos en Tokio.",
                genero = "Comedia/Drama",
                duracion = "1h 42m",
                año = 2003
            )

            10 -> Pelicula(
                id = 10,
                nombre = "Cuestión de Tiempo",
                posterResId = R.drawable.cartelera_cuestion,
                descripcion = "Tim Lake descubre que puede viajar en el tiempo y usa ese poder para conquistar a Mary. Con el tiempo comprende que, aunque puede alterar momentos, no puede evitar las dificultades normales de la vida.",
                genero = "Comedia/Drama",
                duracion = "2h 3m",
                año = 2013
            )

            11 -> Pelicula(
                id = 11,
                nombre = "500 días con ella",
                posterResId = R.drawable.cartelera_500diasconella,
                descripcion = "Tom recuerda los 500 días con Summer para entender por qué su relación terminó y, al hacerlo, redescubre sus verdaderos intereses y propósito.",
                genero = "Comedia/Drama",
                duracion = "1h 42m",
                año = 2009
            )

            12 -> Pelicula(
                id = 12,
                nombre = "Orgullo y Prejuicio",
                posterResId = R.drawable.cartelera_orgulloyprejuicio,
                descripcion = "Elizabeth Bennet conoce al apuesto y adinerado Sr. Darcy, con quien inicia una intensa y compleja relación.",
                genero = "Comedia/Drama",
                duracion = "2h 8m",
                año = 2005
            )

            13 -> Pelicula(
                id = 13,
                nombre = "Eterno Resplandor de una mente sin recuerdos",
                posterResId = R.drawable.cartelera_eternal,
                descripcion = "Una pareja borra sus recuerdos tras una dolorosa ruptura, pero descubren que el destino no puede controlarse.",
                genero = "Drama/Ciencia Ficción",
                duracion = "1h 48m",
                año = 2004
            )

            14 -> Pelicula(
                id = 14,
                nombre = "Requiem por un sueño",
                posterResId = R.drawable.cartelera_requiemforadream,
                descripcion = "Una viuda se vuelve adicta a píldoras dietéticas mientras su hijo enfrenta su propia adicción a las drogas.",
                genero = "Terror/Drama",
                duracion = "1h 42m",
                año = 2000
            )

            15 -> Pelicula(
                id = 15,
                nombre = "American History X",
                posterResId = R.drawable.cartelera_historiamaericana,
                descripcion = "Tras salir de prisión, un exneonazi intenta evitar que su hermano repita sus errores.",
                genero = "Drama/Crimen",
                duracion = "1h 59m",
                año = 1998
            )

            16 -> Pelicula(
                id = 16,
                nombre = "Sueños de Fuga",
                posterResId = R.drawable.cartelera_shawshank,
                descripcion = "En 1947, un hombre inocente es enviado a una corrupta prisión de Maine y sentenciado a dos cadenas perpetuas por un doble asesinato.",
                genero = "Drama/Crimen",
                duracion = "2h 2m",
                año = 1994
            )

            else -> Pelicula(
                id = movieId,
                nombre = "Película $movieId",
                posterResId = R.drawable.ic_logo,
                descripcion = "Descripción de la película $movieId",
                genero = "Género",
                duracion = "2h",
                año = 2024
            )
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = pelicula.nombre,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header de la película
            MovieHeader(pelicula = pelicula)

            // Sección para escribir reseña
            ReviewInputSection(
                rating = uiState.currentRating,
                reviewText = uiState.currentReviewText,
                onRatingChange = reviewViewModel::setRating,
                onReviewTextChange = reviewViewModel::setReviewText,
                onSubmitReview = { reviewViewModel.submitReview(pelicula.id) },
                modifier = Modifier.padding(16.dp)
            )

            // Lista de reseñas existentes
            ReviewsList(
                reviews = uiState.reviews,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun MovieHeader(pelicula: Pelicula) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Fila con poster e información básica
            Row(
                verticalAlignment = Alignment.Top
            ) {
                // Poster de la película
                Card(
                    modifier = Modifier.size(120.dp, 160.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Image(
                        painter = painterResource(pelicula.posterResId),
                        contentDescription = "Poster de ${pelicula.nombre}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Información de la película
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = pelicula.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${pelicula.genero} • ${pelicula.duracion} • ${pelicula.año}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            Text(
                text = "Sinopsis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pelicula.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )
        }
    }
}

@Composable
fun ReviewInputSection(
    rating: Int,
    reviewText: String,
    onRatingChange: (Int) -> Unit,
    onReviewTextChange: (String) -> Unit,
    onSubmitReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Escribe tu reseña",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Calificación:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Estrellas interactivas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 1..5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrella $i",
                        tint = if (i <= rating) Color(0xFFFFC107) else Color.Gray,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onRatingChange(i) }
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tu reseña:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = reviewText,
                onValueChange = onReviewTextChange,
                placeholder = {
                    Text("Escribe tu opinión sobre la película...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                singleLine = false,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onSubmitReview,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = rating > 0 && reviewText.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Publicar reseña",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ReviewsList(
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Reseñas (${reviews.size})",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        if (reviews.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Sin reseñas",
                        tint = Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Aún no hay reseñas\n¡Sé el primero en opinar!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn {
                items(reviews) { review ->
                    ReviewItem(review = review)
                }
            }
        }
    }
}