package com.example.phonecinemaapp.data.repository

import com.example.phonecinema.data.remote.RemoteModule
import com.example.phonecinema.data.remote.UserApi
import com.example.phonecinema.data.dto.UserDto
import com.example.phonecinemaapp.data.remote.dto.UserRegisterDto

class UserRepository(userApi: UserApi) {

    private val api = RemoteModule.create(UserApi::class.java)

    suspend fun getAllUsers(): List<UserDto> {
        return api.getAll()
    }

    suspend fun deleteUser(id: Long) {
        api.delete(id.toString())
    }

    suspend fun updateUser(dto: UserDto) {
        api.update(dto.id.toString(), dto)
    }

    suspend fun getUserById(id: Long): UserDto {
        return api.getById(id.toString())
    }

    suspend fun register(name: String, email: String, password: String): Result<UserDto> {
        return try {
            val body = UserRegisterDto(name, email, password)
            val response = api.register(body)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
