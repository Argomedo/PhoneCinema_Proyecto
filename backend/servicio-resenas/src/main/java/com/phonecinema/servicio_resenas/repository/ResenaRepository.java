package com.phonecinema.servicio_resenas.repository;

import com.phonecinema.servicio_resenas.model.Resena;

import feign.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    List<Resena> findByMovieId(Long movieId);

    @Query("SELECT AVG(r.rating) FROM Resena r WHERE r.movieId = :movieId")
    Double obtenerPromedio(@Param("movieId") Long movieId);

    @Query("SELECT COUNT(r) FROM Resena r WHERE r.movieId = :movieId")
    Long contarResenas(@Param("movieId") Long movieId);

}
