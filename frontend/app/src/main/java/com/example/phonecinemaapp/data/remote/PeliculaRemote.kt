package com.example.phonecinemaapp.data.remote

data class PeliculaRemote(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val genero: String,
    val duracion: String,
    val anio: Int,
    val posterUrl: String
)
