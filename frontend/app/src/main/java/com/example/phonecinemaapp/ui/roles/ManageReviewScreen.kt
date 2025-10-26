package com.example.phonecinemaapp.ui.roles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.phonecinemaapp.data.local.review.ReviewEntity
import com.example.phonecinemaapp.data.repository.ReviewRepository
import com.example.phonecinemaapp.ui.components.AppTopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageReviewsScreen(
    navController: NavController,
    reviewRepo: ReviewRepository,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var reviews by remember { mutableStateOf<List<ReviewEntity>>(emptyList()) }

    // Cargar reseñas al abrir
    LaunchedEffect(Unit) {
        reviews = reviewRepo.getAllReviews()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gestión de Reseñas",
                navController = navController,
                showBackButton = true,
                onBackClick = onBackClick,
                onLogoutClick = onLogoutClick
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
                Text(
                    text = "No hay reseñas disponibles",
                    color = Color(0xFFFAFAFA),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn {
                    items(reviews) { review ->
                        ReviewCard(
                            review = review,
                            onDelete = {
                                scope.launch {
                                    reviewRepo.deleteReview(review)
                                    reviews = reviews.filter { it.id != review.id }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewCard(
    review: ReviewEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF253B76).copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = review.userName,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Puntaje: ${review.rating}/5",
                        color = Color(0xFFFFC107),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (review.comment.isNotBlank()) {
                Text(
                    text = review.comment,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
