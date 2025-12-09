package com.ejemplo.serviciofeedback;

import com.ejemplo.serviciofeedback.client.UsuarioClient;
import com.ejemplo.serviciofeedback.client.UsuarioResponse;
import com.ejemplo.serviciofeedback.dto.FeedbackDTO;
import com.ejemplo.serviciofeedback.model.Feedback;
import com.ejemplo.serviciofeedback.repository.FeedbackRepository;
import com.ejemplo.serviciofeedback.service.FeedbackServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FeedbackServiceImplTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private FeedbackServiceImpl feedbackService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearFeedback_debeCrearYRetornarFeedback() {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setUsuarioId(10L);
        dto.setMensaje("Buen servicio");

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setIdUsuario(10L);
        usuario.setNombre("Juan");

        when(usuarioClient.getUsuario(10L)).thenReturn(usuario);

        Feedback almacenado = new Feedback();
        almacenado.setId(1L);
        almacenado.setUsuarioId(10L);
        almacenado.setNombreUsuario("Juan");
        almacenado.setMensaje("Buen servicio");

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(almacenado);

        Feedback resultado = feedbackService.crearFeedback(dto);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getUsuarioId()).isEqualTo(10L);
        assertThat(resultado.getNombreUsuario()).isEqualTo("Juan");
        assertThat(resultado.getMensaje()).isEqualTo("Buen servicio");

        verify(usuarioClient).getUsuario(10L);
        verify(feedbackRepository).save(any(Feedback.class));
    }

    @Test
    void obtenerFeedbacks_debeRetornarListaCompleta() {
        Feedback f = new Feedback();
        f.setId(1L);
        f.setMensaje("Test");

        when(feedbackRepository.findAll()).thenReturn(List.of(f));

        List<Feedback> resultado = feedbackService.obtenerFeedbacks();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);

        verify(feedbackRepository).findAll();
    }

    @Test
    void obtenerFeedbackPorUsuario_debeRetornarListaDelUsuario() {
        Feedback f = new Feedback();
        f.setUsuarioId(5L);
        f.setMensaje("Mensaje");

        when(feedbackRepository.findByUsuarioId(5L)).thenReturn(List.of(f));

        List<Feedback> resultado = feedbackService.obtenerFeedbackPorUsuario(5L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getUsuarioId()).isEqualTo(5L);

        verify(feedbackRepository).findByUsuarioId(5L);
    }
}
