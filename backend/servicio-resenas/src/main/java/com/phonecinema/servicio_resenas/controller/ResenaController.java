package com.phonecinema.servicio_resenas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonecinema.servicio_resenas.dto.ResenaDTO;
import com.phonecinema.servicio_resenas.model.Resena;
import com.phonecinema.servicio_resenas.service.ResenaService;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @PostMapping("/crear")
    public Resena crearResena(@RequestBody ResenaDTO resenaDTO) {
        return resenaService.crearResena(resenaDTO);
    }
}