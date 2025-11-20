package com.example.phonecinema.data.remote

import com.example.phonecinema.data.dto.AuthRequestDto
import com.example.phonecinema.data.dto.AuthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body body: AuthRequestDto): AuthResponseDto
}
