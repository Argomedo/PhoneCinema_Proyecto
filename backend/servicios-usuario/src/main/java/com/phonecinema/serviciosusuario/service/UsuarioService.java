package com.phonecinema.serviciosusuario.service;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.AuthResponseDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(UsuarioRegistroDTO registroDTO);
    AuthResponseDTO loginUsuario(LoginDTO loginDTO);
}
