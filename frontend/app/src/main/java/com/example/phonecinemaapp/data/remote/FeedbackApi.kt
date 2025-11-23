package com.example.phonecinema.data.remote

import com.example.phonecinema.data.dto.FeedbackDto
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedbackApi {

    @POST("/api/feedback")
    suspend fun enviarFeedback(@Body request: FeedbackDto) // Enviar el feedback al backend
}
