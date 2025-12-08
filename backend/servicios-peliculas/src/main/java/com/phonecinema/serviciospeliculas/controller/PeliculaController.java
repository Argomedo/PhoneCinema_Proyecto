package com.phonecinema.serviciospeliculas.controller;

import com.phonecinema.serviciospeliculas.dto.PeliculaCreateDTO;
import com.phonecinema.serviciospeliculas.dto.PeliculaDTO;
import com.phonecinema.serviciospeliculas.dto.PeliculaupdateDTO;
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

        @PostMapping
    public PeliculaDTO create(@RequestBody PeliculaCreateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public PeliculaDTO update(@PathVariable Long id, @RequestBody PeliculaupdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
}

}
