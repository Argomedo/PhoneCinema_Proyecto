package com.example.phonecinema.data.remote

import com.example.phonecinema.data.dto.MovieDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movies")
    suspend fun getAll(): List<MovieDto>

    @GET("movies/{id}")
    suspend fun getById(@Path("id") id: Long): MovieDto
}
