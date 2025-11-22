package com.phonecinema.serviciosusuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.AuthResponseDTO;
import com.phonecinema.serviciosusuario.dto.TokenDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.model.Usuario;
import com.phonecinema.serviciosusuario.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public Usuario registrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {
        return usuarioService.registrarUsuario(registroDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginDTO loginDTO) {
        try {
            AuthResponseDTO respuesta = usuarioService.loginUsuario(loginDTO);
            return ResponseEntity.ok(respuesta);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body(new TokenDTO(null, "Credenciales inválidas"));
        }
    }
}
