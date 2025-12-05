package com.example.phonecinemaapp.data.repository

import AuthResponseDto
import com.example.phonecinema.data.dto.AuthRequestDto
import com.example.phonecinema.data.remote.AuthApi
import com.example.phonecinema.data.remote.RemoteModule
import com.example.phonecinemaapp.data.local.user.UserEntity
import com.example.phonecinemaapp.data.session.UserSession

class AuthRepository {

    private val api = RemoteModule.createUsuarios(AuthApi::class.java)

    suspend fun login(email: String, password: String): AuthResponseDto {
        val body = AuthRequestDto(email = email, password = password)
        val response = api.login(body)

        // Guardar los datos del usuario en la sesión global
        UserSession.setUser(
            UserEntity(
                id = response.id,
                nombre = response.nombre,
                email = response.email,
                fotoPerfilUrl = response.fotoPerfilUrl ?: "",
                rol = response.rol
            )
        )

        return response
    }
}
