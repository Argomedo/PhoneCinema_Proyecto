package com.example.phonecinema.data.remote

import com.example.phonecinemaapp.data.remote.PeliculaRemote
import retrofit2.http.GET
import retrofit2.http.Path

interface PeliculaApi {

    @GET("api/peliculas")
    suspend fun getAll(): List<PeliculaRemote>

    @GET("api/peliculas/{id}")
    suspend fun getById(@Path("id") id: Int): PeliculaRemote

    @GET("api/peliculas/genero/{genero}")
    suspend fun getByGenero(@Path("genero") genero: String): List<PeliculaRemote>
}
