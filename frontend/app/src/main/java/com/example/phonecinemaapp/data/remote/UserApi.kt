package com.example.phonecinema.data.remote

import com.example.phonecinema.data.dto.UserDto
import com.example.phonecinemaapp.data.remote.dto.UserRegisterDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @GET("usuarios")
    suspend fun getAll(): List<UserDto>

    @GET("usuarios/{id}")
    suspend fun getById(@Path("id") id: String): UserDto

    @DELETE("usuarios/{id}")
    suspend fun delete(@Path("id") id: String)

    @PUT("usuarios/{id}")
    suspend fun update(@Path("id") id: String, @Body dto: UserDto)

    @POST("usuarios/registrar")
    suspend fun register(@Body body: UserRegisterDto): UserDto
}
