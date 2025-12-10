package com.example.phonecinemaapp.ui.resenas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinema.data.dto.ReviewDto
import com.example.phonecinema.data.repository.ReviewRepository
import com.example.phonecinemaapp.data.remote.dto.PeliculaDTO
import com.example.phonecinemaapp.data.repository.PeliculasRepositoryRemote
import com.example.phonecinemaapp.data.session.UserSession
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ReviewUiState(
    val currentRating: Int = 0,
    val currentReviewText: String = "",
    val reviews: List<ReviewUi> = emptyList()
)

data class RatingUiState(
    val promedio: Double = 0.0,
    val total: Int = 0,
    val loading: Boolean = true
)

class ReviewViewModel(
    private val reviewRepository: ReviewRepository,
    private val peliculasRepository: PeliculasRepositoryRemote
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    private val _pelicula = MutableStateFlow<PeliculaDTO?>(null)
    val pelicula: StateFlow<PeliculaDTO?> = _pelicula.asStateFlow()

    private val _rating = MutableStateFlow(RatingUiState())
    val rating: StateFlow<RatingUiState> = _rating.asStateFlow()

    // -----------------------------
    // Setters UI
    // -----------------------------

    fun setRating(value: Int) {
        _uiState.update { it.copy(currentRating = value) }
    }

    fun setReviewText(value: String) {
        _uiState.update { it.copy(currentReviewText = value) }
    }

    // -----------------------------
    // LOAD MOVIE
    // -----------------------------

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            try {
                _pelicula.value = peliculasRepository.getById(movieId.toLong())
            } catch (_: Exception) {
                _pelicula.value = null
            }
        }
    }

    // -----------------------------
    // LOAD REVIEWS
    // -----------------------------

    fun loadReviews(movieId: Int) {
        viewModelScope.launch {
            try {
                val dtoList = reviewRepository.getReviews(movieId.toLong())

                val mapped = dtoList.map { dto ->
                    ReviewUi(
                        userName = dto.userName ?: "Usuario",
                        rating = dto.rating,
                        comment = dto.comment,
                        timestamp = dto.timestamp ?: "",
                        fotoUsuario = dto.fotoUsuario ?: ""
                    )
                }

                val sorted = mapped.sortedByDescending { it.timestamp }

                _uiState.update { it.copy(reviews = sorted) }

            } catch (_: Exception) {
                _uiState.update { it.copy(reviews = emptyList()) }
            }
        }
    }

    // -----------------------------
    // ADD REVIEW
    // -----------------------------

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
                    rating = created.rating,
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

                // Se actualiza promedio después de reseña
                loadRating(movieId)

            } catch (_: Exception) { }
        }
    }

    // -----------------------------
    // LOAD RATING AVERAGE
    // -----------------------------

    fun loadRating(movieId: Int) {
        viewModelScope.launch {
            try {
                val data = reviewRepository.getMovieAverage(movieId.toLong())

                _rating.value = RatingUiState(
                    promedio = data.promedio,
                    total = data.totalResenas,
                    loading = false
                )

            } catch (_: Exception) {
                _rating.value = _rating.value.copy(loading = false)
            }
        }
    }
}
