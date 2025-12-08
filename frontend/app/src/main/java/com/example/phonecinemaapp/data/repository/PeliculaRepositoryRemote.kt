package com.example.phonecinemaapp.data.repository

import com.example.phonecinemaapp.data.remote.PeliculaApi
import com.example.phonecinemaapp.data.remote.dto.*

class PeliculasRepositoryRemote(
    private val api: PeliculaApi
) {
    suspend fun getAll() = api.getAll()
    suspend fun getById(id: Long) = api.getById(id)
    suspend fun create(dto: PeliculaCreateDTO) = api.create(dto)
    suspend fun update(id: Long, dto: PeliculaUpdateDTO) = api.update(id, dto)
    suspend fun delete(id: Long) = api.delete(id)
}

