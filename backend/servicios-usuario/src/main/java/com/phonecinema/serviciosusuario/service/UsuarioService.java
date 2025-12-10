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
    Usuario obtenerPorId(Integer id);   // ✔ agregado
    void eliminarUsuario(Integer id);
    Usuario actualizarRol(Integer id, String nuevoRol);
    void cambiarPassword(Integer id, String actual, String nueva);
    Usuario actualizarFoto(Integer id, String nuevaFotoUrl);


}
