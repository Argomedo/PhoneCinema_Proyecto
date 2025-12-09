package com.ejemplo.serviciofeedback;

import com.ejemplo.serviciofeedback.controller.FeedbackController;
import com.ejemplo.serviciofeedback.dto.FeedbackDTO;
import com.ejemplo.serviciofeedback.model.Feedback;
import com.ejemplo.serviciofeedback.service.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearFeedback_debeRetornarFeedbackCreado() throws Exception {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setMensaje("Buen servicio");
        dto.setUsuarioId(1L);

        Feedback feedback = new Feedback();
        feedback.setId(1L);
        feedback.setMensaje("Buen servicio");
        feedback.setUsuarioId(1L);

        Mockito.when(feedbackService.crearFeedback(any(FeedbackDTO.class)))
                .thenReturn(feedback);

        mockMvc.perform(post("/api/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.mensaje").value("Buen servicio"))
                .andExpect(jsonPath("$.usuarioId").value(1L));
    }

    @Test
    void obtenerFeedbacks_debeRetornarLista() throws Exception {
        Feedback f = new Feedback();
        f.setId(1L);
        f.setMensaje("Excelente");
        f.setUsuarioId(10L);

        Mockito.when(feedbackService.obtenerFeedbacks())
                .thenReturn(List.of(f));

        mockMvc.perform(get("/api/feedback"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].mensaje").value("Excelente"))
                .andExpect(jsonPath("$[0].usuarioId").value(10L));
    }

    @Test
    void obtenerFeedbackPorUsuario_debeRetornarLista() throws Exception {
        Feedback f = new Feedback();
        f.setId(1L);
        f.setMensaje("Correcto");
        f.setUsuarioId(5L);

        Mockito.when(feedbackService.obtenerFeedbackPorUsuario(eq(5L)))
                .thenReturn(List.of(f));

        mockMvc.perform(get("/api/feedback/usuario/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(5L));
    }
}
