package com.phonecinema.servicio_resenas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RatingResponse {
    private Double promedio;
    private Long totalResenas;
}
