package com.example.phonecinemaapp.data.remote.dto

data class PeliculaUpdateDTO(
    val nombre: String,
    val descripcion: String,
    val genero: String,
    val duracion: String,
    val anio: Int,
    val posterUrl: String
)
