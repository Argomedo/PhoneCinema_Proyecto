package com.example.uinavegacion.data.remote.dto

// Línea 1: data class que representa la forma del JSON en /posts
// Línea 2: JSONPlaceholder entrega: userId, id, title, body
data class PostDto(
    val userId: Int, // Línea 4: id del autor del post
    val id: Int,     // Línea 5: id del post
    val title: String, // Línea 6: título del post
    val body: String   // Línea 7: contenido del post
)