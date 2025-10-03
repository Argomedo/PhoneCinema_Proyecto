package com.phonecinema.serviciosusuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phonecinema.serviciosusuario.model.Usuario;

// Al extender JpaRepository, Spring nos da toda la funcionalidad CRUD
// (Crear, Leer, Actualizar, Borrar) de forma automática.
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
    
}