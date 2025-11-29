package com.example.phonecinema.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RemoteModule {

    private const val BASE_URL_USUARIOS = "http://10.0.2.2:8081/api/"
    private const val BASE_URL_RESENAS = "http://10.0.2.2:8082/"
    private const val BASE_URL_FEEDBACK = "http://10.0.2.2:8083/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofitUsuarios: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_USUARIOS)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitResenas: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_RESENAS)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitFeedback: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_FEEDBACK) // Usamos la URL del nuevo microservicio
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Funciones para crear los servicios
    fun <T> createUsuarios(service: Class<T>): T = retrofitUsuarios.create(service)
    fun <T> createResenas(service: Class<T>): T = retrofitResenas.create(service)
    fun <T> createFeedback(service: Class<T>): T = retrofitFeedback.create(service) // Nueva función para Feedback
}
