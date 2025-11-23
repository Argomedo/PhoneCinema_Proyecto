package com.example.phonecinema.data.dto

data class FeedbackDto(
    val usuarioId: Long,   // El ID del usuario logueado
    val userName: String,  // Nombre del usuario
    val fotoUsuario: String, // Foto de perfil del usuario
    val mensaje: String    // El mensaje del feedback
)
