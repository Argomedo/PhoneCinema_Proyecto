package com.example.phonecinemaapp.data.session

import com.example.phonecinemaapp.data.local.user.UserEntity

object UserSession {
    var currentUser: UserEntity? = null

    fun setUser(user: UserEntity) {
        currentUser = user
    }

    fun isAdmin() = currentUser?.rol == "Admin"
    fun isModerator() = currentUser?.rol == "Moderador"
    fun isUser() = currentUser?.rol == "Usuario"
}
