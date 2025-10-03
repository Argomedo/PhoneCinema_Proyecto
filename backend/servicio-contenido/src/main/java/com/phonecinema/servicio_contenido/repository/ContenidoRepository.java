package com.phonecinema.servicio_contenido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.phonecinema.servicio_contenido.model.Contenido;

public interface ContenidoRepository extends JpaRepository<Contenido, Integer> {
    // Spring Data JPA crea los métodos (save, findById, etc.) automáticamente
}