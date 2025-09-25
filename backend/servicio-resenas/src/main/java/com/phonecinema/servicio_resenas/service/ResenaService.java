package com.phonecinema.servicio_resenas.service;

import com.phonecinema.servicio_resenas.dto.ResenaDTO;
import com.phonecinema.servicio_resenas.model.Resena;

public interface ResenaService {
    Resena crearResena(ResenaDTO resenaDTO);
}