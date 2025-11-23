package com.example.phonecinemaapp.data.local.user


data class UserEntity(
    val id: Long = 0L,
    val nombre: String = "",
    val email: String = "",
    val fotoPerfilUrl: String = "",
    val rol: String = "Usuario"
)