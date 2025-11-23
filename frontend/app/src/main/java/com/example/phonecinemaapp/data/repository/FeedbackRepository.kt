package com.example.phonecinemaapp.data.repository

import com.example.phonecinema.data.dto.FeedbackDto
import com.example.phonecinema.data.remote.FeedbackApi
import com.example.phonecinema.data.remote.RemoteModule

class FeedbackRepository {

    private val api = RemoteModule.createFeedback(FeedbackApi::class.java)

    suspend fun sendFeedback(feedback: FeedbackDto) {
        // Enviar el feedback al microservicio de Spring Boot
        api.enviarFeedback(feedback)
    }
}
