package com.phonecinema.serviciosusuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.model.Usuario;
import com.phonecinema.serviciosusuario.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios") // <-- VERIFICA ESTA LÍNEA
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public Usuario registrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {
        return usuarioService.registrarUsuario(registroDTO);
    }

    @PostMapping("/login") // <-- Y VERIFICA ESTA LÍNEA
    public String loginUsuario(@RequestBody LoginDTO loginDTO) {
        return usuarioService.loginUsuario(loginDTO);
    }
}