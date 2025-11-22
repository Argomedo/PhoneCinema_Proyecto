package com.example.phonecinema.data.repository

import ReviewApi
import com.example.phonecinema.data.dto.ReviewDto
import com.example.phonecinema.data.remote.RemoteModule

class ReviewRepository(private val api: ReviewApi) {

    suspend fun getReviews(movieId: Long): List<ReviewDto> {
        return api.getByMovie(movieId)
    }

    suspend fun createReview(review: ReviewDto): ReviewDto {
        return api.create(review)
    }

    suspend fun getAllReviews(): List<ReviewDto> {
        return api.getAll()
    }

    suspend fun deleteReview(id: Long) {
        api.delete(id)
    }
}

