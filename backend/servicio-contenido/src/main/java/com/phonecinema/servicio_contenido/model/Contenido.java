package com.phonecinema.servicio_contenido.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContenido;

    private String titulo;

    @Lob // Para textos largos
    private String sinopsis;
    
    private String portadaUrl;
    private String bannerUrl;
    private Integer anoEstreno;
    private String categoria;
    private String tipo; // "PELICULA" o "SERIE"
}