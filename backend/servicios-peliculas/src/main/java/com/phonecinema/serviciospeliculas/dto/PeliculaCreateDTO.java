package com.phonecinema.serviciospeliculas.dto;

import lombok.Data;

@Data
public class PeliculaCreateDTO {
    private String nombre;
    private String descripcion;
    private String genero;
    private String duracion;
    private Integer anio;
    private String posterUrl;
}
