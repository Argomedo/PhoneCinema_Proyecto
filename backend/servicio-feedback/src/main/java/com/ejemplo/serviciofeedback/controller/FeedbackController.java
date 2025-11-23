package com.ejemplo.serviciofeedback.controller;

import com.ejemplo.serviciofeedback.dto.FeedbackDTO;
import com.ejemplo.serviciofeedback.model.Feedback;
import com.ejemplo.serviciofeedback.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<Feedback> crearFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.crearFeedback(feedbackDTO);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> obtenerFeedbacks() {
        List<Feedback> feedbacks = feedbackService.obtenerFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Feedback>> obtenerFeedbackPorUsuario(@PathVariable Long usuarioId) {
        List<Feedback> feedbacks = feedbackService.obtenerFeedbackPorUsuario(usuarioId);
        return ResponseEntity.ok(feedbacks);
    }
}
