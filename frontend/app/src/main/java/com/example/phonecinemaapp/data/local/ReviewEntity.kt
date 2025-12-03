package com.example.phonecinemaapp.data.local.review.ReviewEntity

data class ReviewEntity(
    val id: Long,
    val movieId: Int,
    val userId: Long,
    val userName: String,
    val fotoUsuario: String = "",
    val rating: Int,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis()
)

