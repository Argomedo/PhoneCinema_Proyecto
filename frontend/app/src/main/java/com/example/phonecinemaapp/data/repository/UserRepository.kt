package com.example.phonecinemaapp.data.repository

import com.example.phonecinema.data.remote.RemoteModule
import com.example.phonecinema.data.remote.UserApi
import com.example.phonecinema.data.dto.UserDto
import com.example.phonecinemaapp.data.remote.dto.UserRegisterDto
import com.example.phonecinemaapp.data.session.UserSession
import com.example.phonecinemaapp.data.local.user.UserEntity

class UserRepository(userApi: UserApi) {

    private val api = RemoteModule.create(UserApi::class.java)

    suspend fun saveUser(user: UserEntity) {
        UserSession.setUser(user)
    }

    suspend fun getAllUsers(): List<UserDto> = api.getAll()

    suspend fun deleteUser(id: Long) = api.delete(id.toString())

    suspend fun updateUser(dto: UserDto) = api.update(dto.id.toString(), dto)

    suspend fun getUserById(id: Long): UserDto = api.getById(id.toString())

    suspend fun register(nombre: String, email: String, password: String): Result<UserDto> =
        try {
            val body = UserRegisterDto(nombre, email, password, confirmPassword = password)
            Result.success(api.register(body))
        } catch (e: Exception) {
            Result.failure(e)
        }
}

