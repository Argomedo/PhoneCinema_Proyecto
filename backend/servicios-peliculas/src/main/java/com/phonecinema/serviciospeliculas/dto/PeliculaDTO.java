package com.phonecinema.serviciospeliculas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String genero;
    private String duracion;
    private Integer anio;      // mejor "anio" que "año" para JSON
    private String posterUrl;
}
