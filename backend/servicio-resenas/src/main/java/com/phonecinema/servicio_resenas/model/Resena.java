package com.phonecinema.servicio_resenas.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idResena;

    private Integer idUsuario;   // FK que conecta con el microservicio de Usuarios
    private Integer idContenido; // FK que conecta con el microservicio de Contenido

    private int calificacion;    // Calificación de 1 a 5
    private String texto;
    private LocalDateTime fecha; // Para guardar la fecha y hora de la reseña
}