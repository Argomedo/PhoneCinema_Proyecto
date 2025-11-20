package com.example.phonecinemaapp.data.local.user

import com.example.phonecinema.data.dto.UserDto

data class UserEntity(
    val id: Long = 0L,
    val name: String,
    val email: String,
    val password: String,
    val photousuario: String = "",
    val role: String = "Usuario"
)

fun UserEntity.toDto(): UserDto =
    UserDto(
        id = this.id,
        username = this.name,
        email = this.email,
        role = this.role
    )

