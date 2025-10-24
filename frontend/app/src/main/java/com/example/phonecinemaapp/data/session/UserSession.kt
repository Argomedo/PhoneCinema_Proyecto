package com.example.phonecinemaapp.data.session

import com.example.phonecinemaapp.data.local.user.UserEntity

object UserSession {
    var currentUser: UserEntity? = null

    fun isAdmin() = currentUser?.role == "Admin"
    fun isModerator() = currentUser?.role == "Moderador"
    fun isUser() = currentUser?.role == "Usuario"
}
