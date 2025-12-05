package com.phonecinema.serviciosusuario.dto;

import lombok.Data;

@Data
public class CambiarPasswordDTO {
    private String passwordActual;
    private String passwordNueva;
}
