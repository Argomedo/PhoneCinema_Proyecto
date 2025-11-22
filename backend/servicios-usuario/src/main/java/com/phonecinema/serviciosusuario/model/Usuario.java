package com.phonecinema.serviciosusuario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Anotación de Lombok para generar getters, setters, etc.
@Entity // Le dice a JPA que esta clase es una tabla de la base de datos
public class Usuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hace que el ID sea autoincremental
    private Integer idUsuario;
    private String nombre;
    private String email;
    private String password;
    private String fotoPerfilUrl;
    private String rol; // Rol del usuario ( "USUARIO", "ADMIN" y "MODERADOR")
}