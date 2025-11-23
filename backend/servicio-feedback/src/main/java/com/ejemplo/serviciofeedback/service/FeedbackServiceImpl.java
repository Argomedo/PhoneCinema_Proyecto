package com.ejemplo.serviciofeedback.service;

import com.ejemplo.serviciofeedback.dto.FeedbackDTO;
import com.ejemplo.serviciofeedback.model.Feedback;
import com.ejemplo.serviciofeedback.repository.FeedbackRepository;
import com.ejemplo.serviciofeedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public Feedback crearFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setUsuarioId(feedbackDTO.getUsuarioId());
        feedback.setMensaje(feedbackDTO.getMensaje());

        // Si la fecha no se pasa desde el DTO, se asigna la fecha actual
        if (feedbackDTO.getFecha() == null) {
            feedback.setFecha(LocalDateTime.now());
        } else {
            feedback.setFecha(feedbackDTO.getFecha());
        }

        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> obtenerFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> obtenerFeedbackPorUsuario(Long usuarioId) {
        return feedbackRepository.findByUsuarioId(usuarioId);
    }
}
