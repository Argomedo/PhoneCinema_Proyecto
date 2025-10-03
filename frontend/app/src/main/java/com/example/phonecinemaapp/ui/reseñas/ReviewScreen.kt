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
                nombre = "Lost in Translation",
                posterResId = R.drawable.cartelera_lost,
                descripcion = "Una amistad inusual se forma entre dos americanos en Tokio.",
                genero = "Comedia/Drama",
                duracion = "1h 42m",
                año = 2003
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