package com.phonecinema.serviciosusuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.AuthResponseDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.model.Rol;
import com.phonecinema.serviciosusuario.model.Usuario;
import com.phonecinema.serviciosusuario.repository.RolRepository;
import com.phonecinema.serviciosusuario.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(UsuarioRegistroDTO registroDTO) {
        validarPassword(registroDTO.getPassword());

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(registroDTO.getNombre());
        nuevoUsuario.setEmail(registroDTO.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        nuevoUsuario.setFotoPerfilUrl(registroDTO.getFotoPerfilUrl());

        Rol rol = rolRepository.findByNombre(registroDTO.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        nuevoUsuario.setRol(rol);

        return usuarioRepository.save(nuevoUsuario);
    }

    @Override
    public AuthResponseDTO loginUsuario(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail());

        if (usuario != null && passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
            return new AuthResponseDTO(
                    usuario.getIdUsuario().longValue(),
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getRol().getNombre(),
                    usuario.getFotoPerfilUrl(),
                    null
            );
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario actualizarRol(Integer id, String nuevoRol) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findByNombre(nuevoRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        return usuarioRepository.save(usuario);
    }

    // Validación
    private void validarPassword(String password) {
        if (
                password.length() < 8 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[a-z].*") ||
                !password.matches(".*\\d.*") ||
                !password.matches(".*[^A-Za-z0-9].*") ||
                password.contains(" ")
        ) {
            throw new IllegalArgumentException("La contraseña no cumple requisitos");
        }
    }

    @Override
public void cambiarPassword(Integer id, String actual, String nueva) {

    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // Validar contraseña actual
    if (!passwordEncoder.matches(actual, usuario.getPassword())) {
        throw new RuntimeException("La contraseña actual es incorrecta");
    }

    // Validar nueva contraseña con tus reglas
    validarPassword(nueva);

    // Guardar contraseña nueva encriptada
    usuario.setPassword(passwordEncoder.encode(nueva));
    usuarioRepository.save(usuario);
}

@Override
public Usuario actualizarFoto(Integer id, String nuevaFotoUrl) {

    Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    usuario.setFotoPerfilUrl(nuevaFotoUrl);

    return usuarioRepository.save(usuario);
}


}
