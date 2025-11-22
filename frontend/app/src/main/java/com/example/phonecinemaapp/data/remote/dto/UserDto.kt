package com.example.phonecinema.data.dto

data class UserDto(
    val id: Long,
    val nombre: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val fotoPerfilUrl: String?,
    val rol: String
)

