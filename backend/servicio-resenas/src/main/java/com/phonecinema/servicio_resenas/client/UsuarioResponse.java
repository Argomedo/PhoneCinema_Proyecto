package com.phonecinema.servicio_resenas.client;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long idUsuario;
    private String nombre;
    private String email;
    private String fotoPerfilUrl;

    // aquí cambiamos el tipo para que coincida con la respuesta real:
    private RolDTO rol;

    @Data
    public static class RolDTO {
        private Integer idRol;
        private String nombre;
    }
}
