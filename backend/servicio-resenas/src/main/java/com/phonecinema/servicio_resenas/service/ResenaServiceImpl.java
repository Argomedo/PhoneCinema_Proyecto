package com.phonecinema.servicio_resenas.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonecinema.servicio_resenas.dto.ResenaDTO;
import com.phonecinema.servicio_resenas.model.Resena;
import com.phonecinema.servicio_resenas.repository.ResenaRepository;

@Service
public class ResenaServiceImpl implements ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public Resena crearResena(ResenaDTO resenaDTO) {
        Resena nuevaResena = new Resena();
        nuevaResena.setIdUsuario(resenaDTO.getIdUsuario());
        nuevaResena.setIdContenido(resenaDTO.getIdContenido());
        nuevaResena.setCalificacion(resenaDTO.getCalificacion());
        nuevaResena.setTexto(resenaDTO.getTexto());

        // Asignamos la fecha y hora actual al momento de crear la reseña
        nuevaResena.setFecha(LocalDateTime.now());

        return resenaRepository.save(nuevaResena);
    }
}