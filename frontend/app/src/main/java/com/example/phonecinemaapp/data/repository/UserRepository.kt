package com.example.phonecinemaapp.data.repository

import com.example.phonecinemaapp.data.local.user.UserDao
import com.example.phonecinemaapp.data.local.user.UserEntity

class UserRepository(
    private val userDao: UserDao
) {
    // --- LOGIN ---
    suspend fun login(email: String, password: String): Result<UserEntity> {
        val user = userDao.getByEmail(email)
        return if (user != null && (password.isBlank() || user.password == password)) {
            Result.success(user)
        } else {
            Result.failure(Exception("Credenciales inválidas"))
        }
    }

    // --- REGISTRO DE USUARIOS COMUNES ---
    suspend fun register(name: String, email: String, password: String): Result<Long> {
        if (userDao.getByEmail(email) != null) {
            return Result.failure(Exception("El correo ya está registrado"))
        }
        val user = UserEntity(
            name = name,
            email = email,
            password = password,
            role = "Usuario" // todos los registros normales son usuarios
        )
        val id = userDao.insert(user)
        return Result.success(id)
    }

    // --- INSERCIÓN DIRECTA CON ROL PERSONALIZADO (Admin / Moderador) ---
    suspend fun insertUser(name: String, email: String, password: String, role: String): Long {
        val user = UserEntity(
            name = name,
            email = email,
            password = password,
            role = role
        )
        return userDao.insert(user)
    }

    // --- ACTUALIZAR DATOS ---
    suspend fun updateUser(user: UserEntity) {
        userDao.update(user)
    }

    // --- BUSCAR USUARIO POR EMAIL ---
    suspend fun getUserByEmail(email: String): UserEntity? {
        return userDao.getByEmail(email)
    }

    // --- ACTUALIZAR FOTO DE PERFIL ---
    suspend fun actualizarFotoUsuario(email: String, nuevaFotoUri: String) {
        val user = userDao.getByEmail(email)
        if (user != null) {
            val actualizado = user.copy(photousuario = nuevaFotoUri)
            userDao.update(actualizado)
        }
    }

    // --- OBTENER TODOS LOS USUARIOS ---
    suspend fun getAllUsers(): List<UserEntity> {
        return userDao.getAllUsers()
    }

    // --- ELIMINAR USUARIO ---
    suspend fun deleteUser(user: UserEntity) {
        userDao.delete(user)
    }


}
