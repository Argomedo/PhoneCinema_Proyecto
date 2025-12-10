package com.phonecinema.servicio_resenas.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

import com.phonecinema.servicio_resenas.model.Resena;

@Data
public class ResenaDTO {

    private Long id;

    @NotNull
    private Long movieId;

    @NotNull
    private Long userId;

    private String userName;

    private String fotoUsuario;   // ← AGREGAR ESTO

    @NotNull
    @Min(1) @Max(5)
    private Integer rating;

    @Size(max = 500)
    private String comment;

    private LocalDateTime timestamp;
}




