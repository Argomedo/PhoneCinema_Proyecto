package com.ejemplo.serviciofeedback.repository;

import com.ejemplo.serviciofeedback.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUsuarioId(Long usuarioId);
}
