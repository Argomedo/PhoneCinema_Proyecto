package com.phonecinema.serviciosusuario.dto;

import lombok.Data;

@Data
public class TokenDTO {
    private String token;
    private String mensaje;

    public TokenDTO(String token, String mensaje) {
        this.token = token;
        this.mensaje = mensaje;
    }
}