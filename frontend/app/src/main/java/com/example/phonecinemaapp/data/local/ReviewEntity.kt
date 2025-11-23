package com.example.phonecinemaapp.data.local.review.ReviewEntity

data class ReviewEntity(
    val id: Long = 0L,
    val movieId: Int,
    val userId: Long,
    val userName: String,
    val fotoUsuario: String = "",
    val rating: Float,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis()
)

