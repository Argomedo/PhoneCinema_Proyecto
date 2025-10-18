package com.example.phonecinemaapp.ui.reseñas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinemaapp.data.local.review.ReviewEntity
import com.example.phonecinemaapp.data.repository.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReviewUiState(
    val currentRating: Int = 0,
    val currentReviewText: String = "",
    val reviews: List<ReviewEntity> = emptyList()
)

class ReviewViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    fun setRating(value: Int) {
        _uiState.update { it.copy(currentRating = value) }
    }

    fun setReviewText(value: String) {
        _uiState.update { it.copy(currentReviewText = value) }
    }

    fun loadReviews(movieId: Int) {
        viewModelScope.launch {
            val reviews = reviewRepository.getReviewsForMovie(movieId)
            _uiState.update { it.copy(reviews = reviews) }
        }
    }

    fun addReview(review: ReviewEntity) {
        viewModelScope.launch {
            reviewRepository.addReview(review)
            val updated = reviewRepository.getReviewsForMovie(review.movieId)
            _uiState.update {
                it.copy(reviews = updated, currentRating = 0, currentReviewText = "")
            }
        }
    }

    fun setReviews(reviews: List<ReviewEntity>) {
        _uiState.update { it.copy(reviews = reviews) }
    }
}
