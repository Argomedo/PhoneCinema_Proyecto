package com.example.phonecinemaapp.ui.resenas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinema.data.dto.ReviewDto
import com.example.phonecinema.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.session.UserSession
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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
                        timestamp = parseTimestamp(dto.timestamp),
                        fotoUsuario = dto.fotoUsuario ?: ""
                    )
                }
                _uiState.update { it.copy(reviews = mapped.sortedByDescending { it.timestamp }) }
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
                rating = _uiState.value.currentRating.toFloat(),
                comment = _uiState.value.currentReviewText
            )

            try {
                val created = reviewRepository.createReview(dto)
                val newReview = ReviewUi(
                    userName = created.userName ?: usuario.nombre,
                    rating = created.rating.toInt(),
                    comment = created.comment,
                    timestamp = parseTimestamp(created.timestamp),
                    fotoUsuario = created.fotoUsuario ?: usuario.fotoPerfilUrl
                )
                _uiState.update {
                    it.copy(
                        reviews = (it.reviews + newReview).sortedByDescending { r -> r.timestamp },
                        currentRating = 0,
                        currentReviewText = ""
                    )
                }
            } catch (_: Exception) {}
        }
    }

    private fun parseTimestamp(timestamp: String?): Long {
        if (timestamp == null) return System.currentTimeMillis()
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            format.timeZone = TimeZone.getTimeZone("UTC")
            format.parse(timestamp)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
}
