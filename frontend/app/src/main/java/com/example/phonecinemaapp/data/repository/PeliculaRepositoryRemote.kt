package com.example.phonecinemaapp.data.repository

import com.example.phonecinemaapp.data.remote.PeliculaRemote
import com.example.phonecinema.data.remote.PeliculaApi
import com.example.phonecinema.data.remote.RemoteModule.createPeliculas

class PeliculasRepositoryRemote(
    private val api: PeliculaApi
) {

    suspend fun getAllPeliculas(): List<PeliculaRemote> {
        return api.getAll()
    }

    suspend fun getById(id: Int): PeliculaRemote {
        return api.getById(id)
    }

    suspend fun getByGenero(genero: String): List<PeliculaRemote> {
        return api.getByGenero(genero)
    }
}
