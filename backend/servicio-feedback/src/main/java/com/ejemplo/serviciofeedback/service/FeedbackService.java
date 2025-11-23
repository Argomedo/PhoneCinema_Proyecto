package com.ejemplo.serviciofeedback.service;

import com.ejemplo.serviciofeedback.dto.FeedbackDTO;
import com.ejemplo.serviciofeedback.model.Feedback;

import java.util.List;

public interface FeedbackService {
    Feedback crearFeedback(FeedbackDTO feedbackDTO);
    List<Feedback> obtenerFeedbacks();
    List<Feedback> obtenerFeedbackPorUsuario(Long usuarioId);
}
