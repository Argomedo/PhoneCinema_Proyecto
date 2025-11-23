package com.ejemplo.serviciofeedback.dto;

import java.time.LocalDateTime;

public class FeedbackDTO {

    private Long usuarioId;
    private String mensaje;
    
    // No es necesario incluir la fecha aquí ya que se generará automáticamente en el backend
    private LocalDateTime fecha;

    // Getters and Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
