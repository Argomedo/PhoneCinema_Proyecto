package com.ejemplo.serviciofeedback.service;

import com.ejemplo.serviciofeedback.client.UsuarioClient;
import com.ejemplo.serviciofeedback.client.UsuarioResponse;
import com.ejemplo.serviciofeedback.dto.FeedbackDTO;
import com.ejemplo.serviciofeedback.model.Feedback;
import com.ejemplo.serviciofeedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UsuarioClient usuarioClient;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               UsuarioClient usuarioClient) {
        this.feedbackRepository = feedbackRepository;
        this.usuarioClient = usuarioClient;
    }

    @Override
    public Feedback crearFeedback(FeedbackDTO dto) {

        UsuarioResponse usuario = usuarioClient.getUsuario(dto.getUsuarioId());

        Feedback feedback = new Feedback();
        feedback.setUsuarioId(usuario.getIdUsuario());
        feedback.setNombreUsuario(usuario.getNombre());
        feedback.setMensaje(dto.getMensaje());

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
