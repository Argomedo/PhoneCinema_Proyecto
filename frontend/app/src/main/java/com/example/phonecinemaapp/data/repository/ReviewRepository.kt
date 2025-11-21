package com.example.phonecinema.data.repository

import ReviewApi
import com.example.phonecinema.data.dto.ReviewDto
import com.example.phonecinema.data.remote.RemoteModule

class ReviewRepository(reviewApi: ReviewApi) {

    private val api = RemoteModule.create(ReviewApi::class.java)

    // Reseñas por película (usuario normal)
    suspend fun getReviews(movieId: Long): List<ReviewDto> {
        return api.getByMovie(movieId)
    }

    // Crear reseña
    suspend fun createReview(review: ReviewDto): ReviewDto {
        return api.create(review)
    }

    // Todas las reseñas (Admin/Moderador)
    suspend fun getAllReviews(): List<ReviewDto> {
        return api.getAll()
    }

    // Eliminar reseña
    suspend fun deleteReview(id: Long) {
        api.delete(id)
    }
}
