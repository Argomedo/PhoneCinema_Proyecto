package com.phonecinema.serviciosusuario.service;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.model.Usuario;

public interface UsuarioService {
    Usuario registrarUsuario(UsuarioRegistroDTO registroDTO);
    
    // MÉTODO NUEVO AÑADIDO
    String loginUsuario(LoginDTO loginDTO);
}