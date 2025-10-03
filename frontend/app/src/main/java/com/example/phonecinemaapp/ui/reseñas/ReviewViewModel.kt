package com.example.phonecinemaapp.ui.reseñas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

// Modelos de datos para reseñas
data class Review(
    val id: String,
    val movieId: Int,
    val userName: String,
    val rating: Int,
    val comment: String,
    val date: Long
)

data class ReviewUiState(
    val currentRating: Int = 0,
    val currentReviewText: String = "",
    val reviews: List<Review> = emptyList()
)

class ReviewViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    fun setRating(newRating: Int) {
        _uiState.value = _uiState.value.copy(currentRating = newRating)
    }

    fun setReviewText(text: String) {
        _uiState.value = _uiState.value.copy(currentReviewText = text)
    }

    fun submitReview(movieId: Int, userName: String = "Usuario") {
        val currentState = _uiState.value
        if (currentState.currentRating > 0 && currentState.currentReviewText.isNotBlank()) {
            val newReview = Review(
                id = UUID.randomUUID().toString(),
                movieId = movieId,
                userName = userName,
                rating = currentState.currentRating,
                comment = currentState.currentReviewText,
                date = System.currentTimeMillis()
            )

            _uiState.value = currentState.copy(
                reviews = listOf(newReview) + currentState.reviews,
                currentRating = 0,
                currentReviewText = ""
            )
        }
    }
}