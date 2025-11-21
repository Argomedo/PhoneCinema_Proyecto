package com.example.phonecinema.data.dto

data class ReviewDto(
    val id: Long?,
    val movieId: Long,
    val userId: Long,
    val rating: Int,
    val comment: String
)
