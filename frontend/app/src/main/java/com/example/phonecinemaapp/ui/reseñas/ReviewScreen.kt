package com.example.phonecinemaapp.ui.reseñas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.phonecinemaapp.data.PeliculaRepository
import com.example.phonecinemaapp.data.local.database.AppDatabase
import com.example.phonecinemaapp.data.local.review.ReviewEntity
import com.example.phonecinemaapp.data.repository.ReviewRepository
import com.example.phonecinemaapp.ui.home.Pelicula
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    movieId: Int,
    onBackClick: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val reviewRepo = remember { ReviewRepository(db.reviewDao()) }
    val reviewViewModel: ReviewViewModel = viewModel(factory = ReviewViewModelFactory(reviewRepo))

    val uiState by reviewViewModel.uiState.collectAsState()
    val pelicula = remember(movieId) { PeliculaRepository.getById(movieId) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(movieId) {
        val reviews = reviewRepo.getReviewsForMovie(movieId)
        reviewViewModel.setReviews(reviews)
    }

    if (pelicula == null) {
        Text("Película no encontrada", modifier = Modifier.padding(16.dp))
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pelicula.nombre) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD4A106),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                }
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
                    val newReview = ReviewEntity(
                        id = 0,
                        movieId = pelicula.id,
                        userId = 1L, // usuario local o fijo
                        userName = "Usuario",
                        rating = uiState.currentRating.toFloat(),
                        comment = uiState.currentReviewText,
                        timestamp = System.currentTimeMillis()
                    )
                    scope.launch {
                        reviewRepo.addReview(newReview)
                        val updatedReviews = reviewRepo.getReviewsForMovie(pelicula.id)
                        reviewViewModel.setReviews(updatedReviews)
                    }
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = pelicula.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = pelicula.descripcion)
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
            enabled = rating > 0 && reviewText.isNotBlank()
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
