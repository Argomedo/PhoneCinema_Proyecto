package com.phonecinema.serviciospeliculas.service;

import com.phonecinema.serviciospeliculas.dto.PeliculaDTO;
import com.phonecinema.serviciospeliculas.model.Pelicula;
import com.phonecinema.serviciospeliculas.repository.PeliculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService {

    private final PeliculaRepository repo;

    public PeliculaService(PeliculaRepository repo) {
        this.repo = repo;
    }

    public List<PeliculaDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public PeliculaDTO getById(Long id) {
        Pelicula pelicula = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        return toDTO(pelicula);
    }

    public List<PeliculaDTO> getByGenero(String genero) {
        return repo.findByGeneroIgnoreCase(genero)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // --------- MAPEO ---------

    private PeliculaDTO toDTO(Pelicula p) {
        return new PeliculaDTO(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getGenero(),
                p.getDuracion(),
                p.getAnio(),
                p.getPosterUrl()
        );
    }

    public Pelicula toEntity(PeliculaDTO dto) {
        Pelicula p = new Pelicula();
        p.setId(dto.getId());
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setGenero(dto.getGenero());
        p.setDuracion(dto.getDuracion());
        p.setAnio(dto.getAnio());
        p.setPosterUrl(dto.getPosterUrl());
        return p;
    }
}
