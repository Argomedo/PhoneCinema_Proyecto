package com.phonecinema.servicio_contenido.dto;

import lombok.Data;

@Data
public class ContenidoDTO {
    private String titulo;
    private String sinopsis;
    private String portadaUrl;
    private String bannerUrl;
    private Integer anoEstreno;
    private String categoria;
    private String tipo; // "PELICULA" o "SERIE"
}