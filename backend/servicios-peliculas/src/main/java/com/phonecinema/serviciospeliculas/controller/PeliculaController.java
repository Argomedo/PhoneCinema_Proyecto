package com.phonecinema.serviciospeliculas.controller;

import com.phonecinema.serviciospeliculas.dto.PeliculaDTO;
import com.phonecinema.serviciospeliculas.service.PeliculaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

    private final PeliculaService service;

    public PeliculaController(PeliculaService service) {
        this.service = service;
    }

    @GetMapping
    public List<PeliculaDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PeliculaDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/genero/{genero}")
    public List<PeliculaDTO> getByGenero(@PathVariable String genero) {
        return service.getByGenero(genero);
    }
}
