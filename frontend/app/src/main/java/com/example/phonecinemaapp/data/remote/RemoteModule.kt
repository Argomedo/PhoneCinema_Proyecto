package com.example.uinavegacion.data.remote

// Línea 1: importamos Retrofit para construir el cliente HTTP tipado
import retrofit2.Retrofit
// Línea 2: importamos GsonConverterFactory para convertir JSON <-> objetos
import retrofit2.converter.gson.GsonConverterFactory
// Línea 3: importamos OkHttpClient para configurar timeouts y logging
import okhttp3.OkHttpClient
// Línea 4: importamos HttpLoggingInterceptor para ver requests/responses en Logcat (debug)
import okhttp3.logging.HttpLoggingInterceptor
// Línea 5: objeto singleton que expone Retrofit y la API
object RemoteModule {

    // Línea 7: base URL del servicio JSONPlaceholder
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // Línea 9: creamos un interceptor de logging para depurar tráfico HTTP
    private val logging = HttpLoggingInterceptor().apply {
        // Línea 11: nivel BODY muestra todo (headers + cuerpo)
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Línea 14: construimos el cliente OkHttp con el interceptor
    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logging) // Línea 16: agregamos logging
        .build()

    // Línea 19: construimos Retrofit indicando baseURL y convertidor JSON
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // Línea 21: host del servicio
        .client(okHttp) // Línea 22: cliente con logging
        .addConverterFactory(GsonConverterFactory.create()) // Línea 23: usa Gson para JSON
        .build()

    // Línea 26: función para crear una implementación de la interfaz API
    fun <T> create(service: Class<T>): T = retrofit.create(service)
}