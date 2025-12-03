package com.example.phonecinemaapp.ui.resenas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinema.data.dto.ReviewDto
import com.example.phonecinema.data.repository.ReviewRepository
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
                        userName = dto.userName ?: "Usuario",
                        rating = dto.rating.toInt(),
                        comment = dto.comment,
                        timestamp = dto.timestamp ?: "",   // ← ahora String
                        fotoUsuario = dto.fotoUsuario ?: ""
                    )
                }

                // ordenar por timestamp descendente (string ISO-8601 ordena correctamente)
                val sorted = mapped.sortedByDescending { it.timestamp }

                _uiState.update { it.copy(reviews = sorted) }

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
                userName = usuario.nombre,
                fotoUsuario = usuario.fotoPerfilUrl,
                rating = _uiState.value.currentRating,
                comment = _uiState.value.currentReviewText
            )

            try {
                val created = reviewRepository.createReview(dto)

                val newReview = ReviewUi(
                    userName = created.userName ?: usuario.nombre,
                    rating = created.rating.toInt(),
                    comment = created.comment,
                    timestamp = created.timestamp ?: "",
                    fotoUsuario = created.fotoUsuario ?: usuario.fotoPerfilUrl
                )

                val updated = (_uiState.value.reviews + newReview)
                    .sortedByDescending { it.timestamp }

                _uiState.update {
                    it.copy(
                        reviews = updated,
                        currentRating = 0,
                        currentReviewText = ""
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
