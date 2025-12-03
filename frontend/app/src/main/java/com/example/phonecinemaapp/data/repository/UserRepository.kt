package com.example.phonecinemaapp.data.repository

import UserDto
import com.example.phonecinema.data.remote.RemoteModule
import com.example.phonecinema.data.remote.UserApi
import com.example.phonecinemaapp.data.local.user.UserEntity
import com.example.phonecinemaapp.data.session.UserSession

data class UsuarioRegistroDTO(
    val nombre: String,
    val email: String,
    val password: String,
    val fotoPerfilUrl: String = "",
    val rol: String = "USUARIO"
)

class UserRepository(userApi: UserApi) {

    private val api = RemoteModule.createUsuarios(UserApi::class.java)

    suspend fun saveUser(user: UserEntity) {
        UserSession.setUser(user)
    }

    suspend fun getAllUsers(): List<UserDto> = api.getAll()

    suspend fun deleteUser(id: Long) = api.delete(id.toString())

    suspend fun updateUser(id: Long, nuevoRol: String) =
        api.update(id.toString(), nuevoRol)

    suspend fun getUserById(id: Long): UserDto = api.getById(id.toString())

    suspend fun register(nombre: String, email: String, password: String): Result<UserDto> =
        try {
            val body = UsuarioRegistroDTO(
                nombre = nombre.trim(),
                email = email.trim(),
                password = password
            )
            Result.success(api.register(body))
        } catch (e: Exception) {
            Result.failure(e)
        }
}
