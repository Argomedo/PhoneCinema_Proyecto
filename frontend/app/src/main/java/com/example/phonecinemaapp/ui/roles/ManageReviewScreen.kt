package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.phonecinema.data.dto.ReviewDto
import com.example.phonecinema.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.PeliculaRepository
import com.example.phonecinemaapp.ui.theme.PhoneCinemaYellow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageReviewsScreen(
    reviewRepo: ReviewRepository,
    onNavigateBackPanel: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var reviews by remember { mutableStateOf<List<ReviewDto>>(emptyList()) }

    LaunchedEffect(Unit) {
        reviews = runCatching { reviewRepo.getAllReviews() }.getOrElse { emptyList() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Reseñas", color = Color.White) },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = Color(0xFFB23A48)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PhoneCinemaYellow)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            if (reviews.isEmpty()) {
                Text("No hay reseñas disponibles", color = Color.White)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(reviews) { review ->
                        ReviewCard(
                            review = review,
                            movieName = review.movieId?.let { id -> getMovieNameById(id.toInt()) }
                                ?: "Película desconocida",
                            onDelete = {
                                scope.launch {
                                    runCatching { review.id?.let { id -> reviewRepo.deleteReview(id) } }
                                    reviews = reviews.filter { it.id != review.id }
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateBackPanel,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PhoneCinemaYellow)
            ) {
                Text("Volver al Panel", color = Color(0xFF253B76))
            }
        }
    }
}

private fun getMovieNameById(movieId: Int): String {
    return PeliculaRepository.getById(movieId)?.nombre ?: "Película #$movieId"
}

@Composable
fun ReviewCard(
    review: ReviewDto,
    movieName: String,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0E1A3B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(1.dp, Color(0xFFD4A106).copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "Película: $movieName",
                color = Color(0xFFD4A106),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Usuario ID: ${review.userId ?: "Desconocido"}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Puntaje: ${review.rating ?: "?"}/5",
                        color = Color(0xFFFFC107),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar reseña",
                        tint = Color(0xFFB23A48)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "\"${review.comment ?: "Sin comentario"}\"",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
