package com.phonecinema.serviciosusuario.service;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.AuthResponseDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.model.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario registrarUsuario(UsuarioRegistroDTO registroDTO);
    AuthResponseDTO loginUsuario(LoginDTO loginDTO);
    List<Usuario> obtenerTodos();

    // Obligatorios para que los botones funcionen
    void eliminarUsuario(Integer id);
    Usuario actualizarRol(Integer id, String nuevoRol);
}
