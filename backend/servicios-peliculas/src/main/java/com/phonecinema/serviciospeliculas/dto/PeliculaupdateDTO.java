package com.phonecinema.serviciospeliculas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaupdateDTO {

    private String nombre;
    private String descripcion;
    private String genero;
    private String duracion;
    private Integer anio;
    private String posterUrl;
}
