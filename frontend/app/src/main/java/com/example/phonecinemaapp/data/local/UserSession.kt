package com.example.phonecinemaapp.data.session

import com.example.phonecinemaapp.data.local.user.UserEntity

object UserSession {
    var currentUser: UserEntity? = null

    fun setUser(user: UserEntity) {
        currentUser = user
    }

    fun isAdmin() = currentUser?.rol == "ADMIN"
    fun isModerator() = currentUser?.rol == "MODERADOR"
    fun isUser() = currentUser?.rol == "USUARIO"
}
