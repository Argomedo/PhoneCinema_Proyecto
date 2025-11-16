package com.example.uinavegacion.data.remote

// Importamos las anotaciones necesarias para definir endpoints HTTP.
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
// Línea 2: import de nuestro DTO
import com.example.uinavegacion.data.remote.dto.PostDto

// Línea 4: interfaz con endpoints del servicio
interface JsonPlaceholderApi {

    // Línea 6: GET /posts -> devuelve lista de PostDto
    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    // Endpoint para obtener un post por ID: GET /posts/{id}
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): PostDto

    // Endpoint para crear un nuevo post: POST /posts
    @POST("posts")
    suspend fun createPost(@Body post: PostDto): PostDto

    // Endpoint para actualizar un post existente: PUT /posts/{id}
    @PUT("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Body post: PostDto
    ): PostDto

    // Endpoint para eliminar un post: DELETE /posts/{id}
    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}