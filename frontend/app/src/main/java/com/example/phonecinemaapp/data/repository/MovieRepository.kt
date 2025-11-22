package com.example.phonecinemaapp.data.repository

import com.example.phonecinema.data.dto.MovieDto
import com.example.phonecinema.data.remote.MovieApi
import com.example.phonecinema.data.remote.RemoteModule

class MovieRepository {

    private val api = RemoteModule.createUsuarios(MovieApi::class.java)

    suspend fun getAll(): List<MovieDto> {
        return api.getAll()
    }

    suspend fun getById(id: Long): MovieDto {
        return api.getById(id)
    }
}
