package com.example.phonecinema.data.dto

data class MovieDto(
    val id: Long,
    val title: String,
    val description: String,
    val posterUrl: String,
    val genre: String,
    val duration: String,
    val year: Int
)


