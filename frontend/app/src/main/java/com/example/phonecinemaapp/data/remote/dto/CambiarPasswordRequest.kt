package com.example.phonecinemaapp.data.remote.dto

data class CambiarPasswordRequest(
    val passwordActual: String,
    val passwordNueva: String
)
