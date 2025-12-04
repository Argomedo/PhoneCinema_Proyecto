package com.phonecinema.serviciospeliculas.repository;

import com.phonecinema.serviciospeliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

    // Buscar por género exacto
    List<Pelicula> findByGenero(String genero);

    // Buscar ignorando mayúsculas/minúsculas
    List<Pelicula> findByGeneroIgnoreCase(String genero);
}
