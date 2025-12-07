package com.example.phonecinema.data.remote

import com.example.phonecinemaapp.data.remote.PeliculaRemote
import retrofit2.http.Body //dependencia nueva
import retrofit2.http.DELETE //dependencia nueva
import retrofit2.http.GET
import retrofit2.http.POST //dependencia nueva
import retrofit2.http.PUT //dependencia nueva
import retrofit2.http.Path

interface PeliculaApi {

    @GET("api/peliculas")
    suspend fun getAll(): List<PeliculaRemote>

    @GET("api/peliculas/{id}")
    suspend fun getById(@Path("id") id: Int): PeliculaRemote

    @GET("api/peliculas/genero/{genero}")
    suspend fun getByGenero(@Path("genero") genero: String): List<PeliculaRemote>

    //Lo nuevo para agregar/actualizar y eliminar

    @POST("api/peliculas")
    suspend fun create(@Body pelicula: PeliculaRemote): PeliculaRemote

    @PUT("api/peliculas/{id}")
    suspend fun update(@Path("id") id: Int, @Body pelicula: PeliculaRemote): PeliculaRemote

    @DELETE("api/peliculas/{id}")
    suspend fun delete(@Path("id") id: Int)
}
