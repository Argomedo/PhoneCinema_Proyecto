package com.phonecinema.serviciosusuario.dto;

import lombok.Data;

@Data
public class UsuarioRegistroDTO {
private String nombre;
private String email;
private String password;
private String fotoPerfilUrl = ""; // por defecto vacío
private String rol = "USUARIO";
 // por defecto

}
