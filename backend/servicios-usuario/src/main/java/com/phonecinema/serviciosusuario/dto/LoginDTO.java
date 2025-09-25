package com.phonecinema.serviciosusuario.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String contrasena;
}