package com.phonecinema.serviciosusuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.AuthResponseDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.model.Usuario;
import com.phonecinema.serviciosusuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario registrarUsuario(UsuarioRegistroDTO registroDTO) {
        validarPassword(registroDTO.getPassword());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setEmail(registroDTO.getEmail());
        nuevoUsuario.setPassword(registroDTO.getPassword());
        nuevoUsuario.setFotoPerfilUrl(registroDTO.getFotoPerfilUrl());
        nuevoUsuario.setRol(registroDTO.getRol());

        return usuarioRepository.save(nuevoUsuario);
    }

    @Override
    public AuthResponseDTO loginUsuario(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail());

        if (usuario != null && usuario.getPassword().equals(loginDTO.getPassword())) {
            return new AuthResponseDTO(
                usuario.getIdUsuario().longValue(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getFotoPerfilUrl(),
                "token_temporal"
            );
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    private void validarPassword(String password) {
        if (password.length() < 8 ||
            !password.matches(".*[A-Z].*") ||
            !password.matches(".*[a-z].*") ||
            !password.matches(".*\\d.*") ||
            !password.matches(".*[^A-Za-z0-9].*") ||
            password.contains(" ")) {
            throw new IllegalArgumentException("La contraseña no cumple requisitos");
        }
    }
}
