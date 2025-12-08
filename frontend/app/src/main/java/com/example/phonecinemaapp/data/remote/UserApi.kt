package com.example.phonecinema.data.remote

import UserDto
import com.example.phonecinemaapp.data.remote.dto.CambiarPasswordRequest
import com.example.phonecinemaapp.data.repository.UsuarioRegistroDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {

    @GET("usuarios")
    suspend fun getAll(): List<UserDto>

    @GET("usuarios/{id}")
    suspend fun getById(@Path("id") id: String): UserDto

    @DELETE("usuarios/{id}")
    suspend fun delete(@Path("id") id: String)

    @PUT("usuarios/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Query("rol") nuevoRol: String
    ): UserDto

    @POST("usuarios/registrar")
    suspend fun register(@Body body: UsuarioRegistroDTO): UserDto

    // ✔ NUEVO ENDPOINT: cambiar contraseña
    @PUT("usuarios/{id}/password")
    suspend fun cambiarPassword(
        @Path("id") id: String,
        @Body body: CambiarPasswordRequest
    )
}