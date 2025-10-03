package com.phonecinema.servicio_resenas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.phonecinema.servicio_resenas.model.Resena;

public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    // Spring Data JPA crea los métodos (save, findById, etc.) automáticamente
}