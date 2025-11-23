package com.phonecinema.serviciosusuario.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
    private String fotoPerfilUrl;
    private String token;

    public AuthResponseDTO(Long id, String nombre, String email, String rol, String fotoPerfilUrl, String token) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.token = token;
    }
}
