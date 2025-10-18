package com.example.phonecinemaapp.data.repository

import com.example.phonecinemaapp.data.local.user.UserDao
import com.example.phonecinemaapp.data.local.user.UserEntity

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun login(email: String, password: String): Result<UserEntity> {
        val user = userDao.getByEmail(email)
        return if (user != null && (password.isBlank() || user.password == password)) {
            Result.success(user)
        } else {
            Result.failure(Exception("Credenciales inválidas"))
        }
    }

    suspend fun register(name: String, email: String, password: String): Result<Long> {
        if (userDao.getByEmail(email) != null) {
            return Result.failure(Exception("El correo ya está registrado"))
        }
        val user = UserEntity(name = name, email = email, password = password)
        val id = userDao.insert(user)
        return Result.success(id)
    }

    suspend fun updateUser(user: UserEntity) {
        userDao.update(user)
    }

    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getByEmail(email)

    }

}
