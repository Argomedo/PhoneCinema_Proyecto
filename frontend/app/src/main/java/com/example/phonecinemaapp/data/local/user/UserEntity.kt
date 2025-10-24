package com.example.phonecinemaapp.data.local.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val email: String,
    val password: String,
    val photousuario: String= "", // Agrege este campo para las fotos
    val role: String = "Usuario" //  "Admin", "Moderador", "Usuario"
)
