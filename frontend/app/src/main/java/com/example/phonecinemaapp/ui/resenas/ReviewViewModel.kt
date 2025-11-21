package com.example.phonecinemaapp.ui.resenas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinema.data.dto.ReviewDto
import com.example.phonecinema.data.repository.ReviewRepository
import com.example.phonecinemaapp.ui.resenas.ReviewUi
import com.example.phonecinemaapp.data.session.UserSession
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ReviewUiState(
    val currentRating: Int = 0,
    val currentReviewText: String = "",
    val reviews: List<ReviewUi> = emptyList()
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
            try {
                val dtoList = reviewRepository.getReviews(movieId.toLong())

                val mapped = dtoList.map { dto ->
                    ReviewUi(
                        userName = "Usuario ${dto.userId}",  // tu backend no entrega el nombre
                        rating = dto.rating,
                        comment = dto.comment,
                        timestamp = System.currentTimeMillis(),
                        fotoUsuario = ""
                    )
                }

                _uiState.update { it.copy(reviews = mapped) }

            } catch (e: Exception) {
                _uiState.update { it.copy(reviews = emptyList()) }
            }
        }
    }

    fun addReview(movieId: Int) {
        viewModelScope.launch {
            val usuario = UserSession.currentUser ?: return@launch

            val dto = ReviewDto(
                id = null,
                movieId = movieId.toLong(),
                userId = usuario.id,
                rating = _uiState.value.currentRating,
                comment = _uiState.value.currentReviewText
            )

            try {
                reviewRepository.createReview(dto)
                loadReviews(movieId)

                _uiState.update {
                    it.copy(
                        currentRating = 0,
                        currentReviewText = ""
                    )
                }

            } catch (e: Exception) {}
        }
    }
}
