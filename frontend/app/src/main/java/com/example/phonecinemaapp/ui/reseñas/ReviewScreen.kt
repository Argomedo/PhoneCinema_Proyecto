package com.example.phonecinemaapp.ui.reseñas

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.phonecinemaapp.data.PeliculaRepository
import com.example.phonecinemaapp.data.local.database.AppDatabase
import com.example.phonecinemaapp.data.local.review.ReviewEntity
import com.example.phonecinemaapp.data.repository.ReviewRepository
import com.example.phonecinemaapp.ui.components.AppTopBar
import com.example.phonecinemaapp.ui.home.Pelicula

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navController: NavController,
    movieId: Int,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val reviewRepo = remember { ReviewRepository(db.reviewDao()) }
    val reviewViewModel: ReviewViewModel = viewModel(factory = ReviewViewModelFactory(reviewRepo))

    val uiState by reviewViewModel.uiState.collectAsState()
    val pelicula = remember(movieId) { PeliculaRepository.getById(movieId) }

    LaunchedEffect(movieId) {
        reviewViewModel.loadReviews(movieId)
    }

    if (pelicula == null) {
        Text("Película no encontrada", modifier = Modifier.padding(16.dp))
        return
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = pelicula.nombre,
                navController = navController,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = onLogoutClick
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MovieHeader(pelicula)

            ReviewInputSection(
                rating = uiState.currentRating,
                reviewText = uiState.currentReviewText,
                onRatingChange = reviewViewModel::setRating,
                onReviewTextChange = reviewViewModel::setReviewText,
                onSubmitReview = {
                    reviewViewModel.addReview(pelicula.id)
                }
            )

            ReviewsList(reviews = uiState.reviews)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = pelicula.posterResId),
                contentDescription = pelicula.nombre,
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pelicula.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = listOfNotNull(
                        pelicula.genero.takeIf { it.isNotBlank() },
                        pelicula.duracion.takeIf { it.isNotBlank() },
                        pelicula.año.takeIf { it != 0 }?.toString()
                    ).joinToString(" • "),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = pelicula.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
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
        Text("Calificación")
        Row {
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
            label = { Text("Tu reseña") }
        )
        Button(
            onClick = onSubmitReview,
            modifier = Modifier.fillMaxWidth(),
            enabled = rating > 0
        ) {
            Text("Publicar reseña")
        }
    }
}

@Composable
fun ReviewsList(reviews: List<ReviewEntity>) {
    LazyColumn {
        items(reviews) { review ->
            ReviewItem(review = review)
        }
    }
}
