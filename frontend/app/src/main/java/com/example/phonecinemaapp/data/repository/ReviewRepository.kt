package com.example.phonecinemaapp.data.repository

import com.example.phonecinemaapp.data.local.review.ReviewDao
import com.example.phonecinemaapp.data.local.review.ReviewEntity

class ReviewRepository(
    private val reviewDao: ReviewDao
) {
    // Reseñas por película
    suspend fun getReviewsForMovie(movieId: Int): List<ReviewEntity> =
        reviewDao.getReviewsForMovie(movieId)

    // Todas las reseñas (para Admin / Moderador)
    suspend fun getAllReviews(): List<ReviewEntity> =
        reviewDao.getAllReviews()

    // Agregar reseña
    suspend fun addReview(review: ReviewEntity) {
        reviewDao.insert(review)
    }

    // Eliminar reseña
    suspend fun deleteReview(review: ReviewEntity) {
        reviewDao.delete(review)
    }
}
