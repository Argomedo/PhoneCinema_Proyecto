package com.phonecinema.servicio_resenas.repository;

import com.phonecinema.servicio_resenas.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByMovieId(Long movieId);  // ← necesario para tu servicio
}
