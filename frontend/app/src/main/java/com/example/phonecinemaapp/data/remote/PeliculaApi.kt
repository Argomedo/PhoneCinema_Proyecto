package com.example.phonecinemaapp.data.remote

import com.example.phonecinemaapp.data.remote.dto.PeliculaDTO
import com.example.phonecinemaapp.data.remote.dto.PeliculaCreateDTO
import com.example.phonecinemaapp.data.remote.dto.PeliculaUpdateDTO
import retrofit2.http.*

interface PeliculaApi {

    // ---------- GET ----------
    @GET("api/peliculas")
    suspend fun getAll(): List<PeliculaDTO>

    @GET("api/peliculas/{id}")
    suspend fun getById(@Path("id") id: Long): PeliculaDTO

    @GET("api/peliculas/genero/{genero}")
    suspend fun getByGenero(@Path("genero") genero: String): List<PeliculaDTO>

    // ---------- POST ----------
    @POST("api/peliculas")
    suspend fun create(@Body dto: PeliculaCreateDTO): PeliculaDTO

    // ---------- PUT ----------
    @PUT("api/peliculas/{id}")
    suspend fun update(
        @Path("id") id: Long,
        @Body dto: PeliculaUpdateDTO
    ): PeliculaDTO

    // ---------- DELETE ----------
    @DELETE("api/peliculas/{id}")
    suspend fun delete(@Path("id") id: Long)
}
