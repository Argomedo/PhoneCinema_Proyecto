package com.example.phonecinema.data.remote

import AuthResponseDto
import UserDto
import com.example.phonecinema.data.dto.AuthRequestDto
import com.example.phonecinemaapp.data.remote.dto.UserRegisterDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("usuarios/login")
    suspend fun login(@Body body: AuthRequestDto): AuthResponseDto

    @POST("usuarios/registrar")
    suspend fun register(@Body body: UserRegisterDto): UserDto


}