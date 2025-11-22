package com.example.phonecinemaapp.data.repository

import com.example.phonecinema.data.dto.AuthRequestDto
import com.example.phonecinema.data.dto.AuthResponseDto
import com.example.phonecinema.data.remote.AuthApi
import com.example.phonecinema.data.remote.RemoteModule

class AuthRepository {

    private val api = RemoteModule.create(AuthApi::class.java)

    suspend fun login(email: String, password: String): AuthResponseDto {
        val body = AuthRequestDto(
            email = email,
            password = password
        )
        return api.login(body)
    }
}
