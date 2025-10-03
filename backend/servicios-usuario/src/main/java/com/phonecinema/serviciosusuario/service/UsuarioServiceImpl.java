package com.phonecinema.serviciosusuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.model.Usuario;
import com.phonecinema.serviciosusuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario registrarUsuario(UsuarioRegistroDTO registroDTO) {
        // ... (lógica de registro que ya teníamos) ...
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setEmail(registroDTO.getEmail());
        nuevoUsuario.setContrasena(registroDTO.getContrasena()); // Temporalmente sin encriptar
        nuevoUsuario.setRol("USUARIO");
        return usuarioRepository.save(nuevoUsuario);
    }

    // LÓGICA DE LOGIN AÑADIDA
    @Override
    public String loginUsuario(LoginDTO loginDTO) {
        // 1. Buscamos un usuario por su email
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail());

        // 2. Verificamos si el usuario existe y si la contraseña coincide
        if (usuario != null && usuario.getContrasena().equals(loginDTO.getContrasena())) {
            // Si es correcto, devolvemos un "token" de mentira por ahora
            return "login_exitoso_token_temporal";
        } else {
            // Si no, devolvemos un mensaje de error
            return "error_credenciales_invalidas";
        }
    }
}