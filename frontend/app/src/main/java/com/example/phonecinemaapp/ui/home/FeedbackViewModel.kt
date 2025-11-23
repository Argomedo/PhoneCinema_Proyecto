package com.example.phonecinemaapp.ui.feedback

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.phonecinema.data.dto.FeedbackDto
import com.example.phonecinemaapp.data.session.UserSession
import com.example.phonecinemaapp.data.repository.FeedbackRepository
import com.example.phonecinemaapp.data.local.user.UserEntity
import kotlinx.coroutines.launch

class FeedbackViewModel(application: Application) : AndroidViewModel(application) {

    private val feedbackRepository = FeedbackRepository()

    // Obtener el usuario logueado desde UserSession
    fun getUser(): UserEntity? {
        return UserSession.currentUser
    }

    // Enviar el feedback al microservicio
    fun sendFeedback(feedback: FeedbackDto) {
        viewModelScope.launch {
            try {
                feedbackRepository.sendFeedback(feedback)  // Enviar el feedback al backend
                // Aquí puedes manejar la respuesta, como mostrar un mensaje de éxito
            } catch (e: Exception) {
                // Manejo de errores en caso de fallo en la llamada
            }
        }
    }
}
