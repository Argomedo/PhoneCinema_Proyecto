package com.example.phonecinema.data.dto

data class ReviewDto(
    val id: Long? = null,
    val movieId: Long,
    val userId: Long,
    val userName: String? = null,
    val rating: Float,
    val comment: String,
    val timestamp: String? = null,
    val fotoUsuario: String? = null
)





