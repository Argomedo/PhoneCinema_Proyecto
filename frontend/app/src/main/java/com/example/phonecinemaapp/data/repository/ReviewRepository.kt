package com.example.phonecinemaapp.data.repository

import com.example.phonecinemaapp.data.local.review.ReviewDao
import com.example.phonecinemaapp.data.local.review.ReviewEntity

class ReviewRepository(
    private val reviewDao: ReviewDao
) {
    suspend fun getReviewsForMovie(movieId: Int): List<ReviewEntity> =
        reviewDao.getReviewsForMovie(movieId)

    suspend fun addReview(review: ReviewEntity) {
        reviewDao.insert(review)
    }
}
