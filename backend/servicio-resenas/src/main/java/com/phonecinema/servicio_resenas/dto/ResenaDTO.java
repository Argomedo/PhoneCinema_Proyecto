package com.phonecinema.servicio_resenas.dto;

import lombok.Data;

@Data
public class ResenaDTO {
    private Integer idUsuario;
    private Integer idContenido;
    private int calificacion;
    private String texto;
}