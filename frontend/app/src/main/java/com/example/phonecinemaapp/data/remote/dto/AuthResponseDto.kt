package com.example.phonecinema.data.dto

data class AuthResponseDto(
    val id: Long,
    val nombre: String,
    val email: String,
    val fotoPerfilUrl: String?,
    val rol: String,
    val token: String?
)
