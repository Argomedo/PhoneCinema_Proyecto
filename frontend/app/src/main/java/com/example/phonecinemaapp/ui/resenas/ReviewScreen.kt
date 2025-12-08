package com.example.phonecinemaapp.ui.resenas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.phonecinema.data.remote.RemoteModule
import com.example.phonecinemaapp.data.remote.dto.PeliculaDTO
import com.example.phonecinemaapp.ui.components.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavController,
    movieId: Int,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val reviewViewModel: ReviewViewModel = viewModel(
        factory = ReviewViewModelFactory(
            reviewRepository = RemoteModule.reviewRepository,
            peliculasRepository = RemoteModule.peliculasRepository
        )
    )

    val uiState by reviewViewModel.uiState.collectAsState()
    val pelicula by reviewViewModel.pelicula.collectAsState()

    LaunchedEffect(movieId) {
        reviewViewModel.loadMovie(movieId)
        reviewViewModel.loadReviews(movieId)
    }

    if (pelicula == null) {
        Text("Cargando película...", modifier = Modifier.padding(16.dp))
        return
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = pelicula!!.nombre,
                navController = navController,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = onLogoutClick,
                onFeedbackClick = { navController.navigate("feedback_screen") }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            MovieHeader(pelicula!!)

            ReviewInputSection(
                rating = uiState.currentRating,
                reviewText = uiState.currentReviewText,
                onRatingChange = reviewViewModel::setRating,
                onReviewTextChange = reviewViewModel::setReviewText,
                onSubmitReview = { reviewViewModel.addReview(movieId) }
            )

            ReviewsList(uiState.reviews)
        }
    }
}

@Composable
fun MovieHeader(pelicula: PeliculaDTO) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = pelicula.posterUrl,
                contentDescription = pelicula.nombre,
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(pelicula.nombre, style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    "${pelicula.genero} • ${pelicula.duracion} • ${pelicula.anio}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(pelicula.descripcion, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun ReviewInputSection(
    rating: Int,
    reviewText: String,
    onRatingChange: (Int) -> Unit,
    onReviewTextChange: (String) -> Unit,
    onSubmitReview: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Calificación", style = MaterialTheme.typography.titleMedium)

        Row(modifier = Modifier.padding(vertical = 4.dp)) {
            for (i in 1..5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (i <= rating) Color(0xFFFFC107) else Color.Gray,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onRatingChange(i) }
                        .padding(4.dp)
                )
            }
        }

        OutlinedTextField(
            value = reviewText,
            onValueChange = onReviewTextChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Tu reseña") },
            maxLines = 5
        )

        Button(
            onClick = onSubmitReview,
            modifier = Modifier.fillMaxWidth(),
            enabled = rating > 0 && reviewText.isNotBlank()
        ) {
            Text("Publicar reseña")
        }
    }
}

@Composable
fun ReviewsList(reviews: List<ReviewUi>) {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            text = "Reseñas de la comunidad (${reviews.size})",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Divider(modifier = Modifier.padding(horizontal = 16.dp))

        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(reviews) { review ->
                ReviewItem(review)
            }
        }
    }
}
