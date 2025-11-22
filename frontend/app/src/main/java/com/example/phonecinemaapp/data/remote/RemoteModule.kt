package com.example.phonecinema.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object RemoteModule {

    private const val BASE_URL_USUARIOS = "http://192.168.1.37:8081/api/"
    private const val BASE_URL_RESENAS = "http://192.168.1.37:8082/" // ← sin /api

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

    fun <T> createUsuarios(service: Class<T>): T = retrofitUsuarios.create(service)

    fun <T> createResenas(service: Class<T>): T = retrofitResenas.create(service)
}


