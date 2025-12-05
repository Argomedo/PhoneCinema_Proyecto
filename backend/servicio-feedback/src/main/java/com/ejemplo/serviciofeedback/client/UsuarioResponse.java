package com.ejemplo.serviciofeedback.client;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long idUsuario;
    private String nombre;
    private String email;
    private String fotoPerfilUrl;
    private RolDTO rol;

    @Data
    public static class RolDTO {
        private Integer idRol;
        private String nombre;
    }
}