package com.phonecinema.serviciosusuario;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonecinema.serviciosusuario.config.SecurityDisabledConfig;
import com.phonecinema.serviciosusuario.controller.UsuarioController;
import com.phonecinema.serviciosusuario.dto.*;
import com.phonecinema.serviciosusuario.model.Rol;
import com.phonecinema.serviciosusuario.model.Usuario;
import com.phonecinema.serviciosusuario.service.UsuarioService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UsuarioController.class)
@Import(SecurityDisabledConfig.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registrarUsuario_debeRetornarUsuarioCreado() throws Exception {

        UsuarioRegistroDTO dto = new UsuarioRegistroDTO();
        dto.setNombre("Diego");
        dto.setEmail("diego@mail.com");
        dto.setPassword("Test123*");

        Rol rol = new Rol(1, "USUARIO");

        Usuario creado = new Usuario();
        creado.setIdUsuario(1);
        creado.setNombre("Diego");
        creado.setEmail("diego@mail.com");
        creado.setPassword("hashpassword");
        creado.setFotoPerfilUrl("");
        creado.setRol(rol);

        Mockito.when(usuarioService.registrarUsuario(any(UsuarioRegistroDTO.class)))
                .thenReturn(creado);

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nombre").value("Diego"))
                .andExpect(jsonPath("$.email").value("diego@mail.com"))
                .andExpect(jsonPath("$.rol.nombre").value("USUARIO"));
    }

    @Test
    void loginUsuario_debeRetornarAuthResponse() throws Exception {

        LoginDTO login = new LoginDTO();
        login.setEmail("admin@mail.com");
        login.setPassword("Admin123*");

        AuthResponseDTO response = new AuthResponseDTO(
                1L,
                "Admin",
                "admin@mail.com",
                "ADMIN",
                "foto.png",
                "abc123token"
        );

        Mockito.when(usuarioService.loginUsuario(any(LoginDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Admin"))
                .andExpect(jsonPath("$.email").value("admin@mail.com"))
                .andExpect(jsonPath("$.rol").value("ADMIN"))
                .andExpect(jsonPath("$.token").value("abc123token"));
    }

    @Test
    void loginUsuario_debeRetornar401CuandoCredencialesInvalidas() throws Exception {

        LoginDTO dto = new LoginDTO();
        dto.setEmail("wrong@mail.com");
        dto.setPassword("123");

        Mockito.when(usuarioService.loginUsuario(any(LoginDTO.class)))
                .thenThrow(new RuntimeException("Credenciales inválidas"));

        mockMvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.token").doesNotExist())
                .andExpect(jsonPath("$.mensaje").value("Credenciales inválidas"));
    }

    @Test
    void obtenerTodos_debeRetornarListaUsuarios() throws Exception {

        Rol rol = new Rol(1, "USUARIO");

        Usuario u = new Usuario();
        u.setIdUsuario(1);
        u.setNombre("Test");
        u.setEmail("t@mail.com");
        u.setPassword("pass");
        u.setRol(rol);

        Mockito.when(usuarioService.obtenerTodos())
                .thenReturn(List.of(u));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUsuario").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Test"))
                .andExpect(jsonPath("$[0].rol.nombre").value("USUARIO"));
    }

    @Test
    void obtenerPorId_debeRetornarUsuario() throws Exception {

        Rol rol = new Rol(2, "ADMIN");

        Usuario u = new Usuario();
        u.setIdUsuario(5);
        u.setNombre("Carlos");
        u.setEmail("c@mail.com");
        u.setPassword("pass");
        u.setRol(rol);

        Mockito.when(usuarioService.obtenerPorId(5))
                .thenReturn(u);

        mockMvc.perform(get("/api/usuarios/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario").value(5))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.rol.nombre").value("ADMIN"));
    }

    @Test
    void eliminarUsuario_debeRetornar204() throws Exception {

        Mockito.doNothing().when(usuarioService).eliminarUsuario(3);

        mockMvc.perform(delete("/api/usuarios/3"))
                .andExpect(status().isNoContent());
    }

    @Test
    void actualizarRol_debeRetornarUsuarioActualizado() throws Exception {

        Rol rol = new Rol(2, "ADMIN");

        Usuario u = new Usuario();
        u.setIdUsuario(2);
        u.setNombre("Ana");
        u.setEmail("ana@mail.com");
        u.setPassword("pass");
        u.setRol(rol);

        Mockito.when(usuarioService.actualizarRol(eq(2), eq("ADMIN")))
                .thenReturn(u);

        mockMvc.perform(put("/api/usuarios/2?rol=ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rol.nombre").value("ADMIN"))
                .andExpect(jsonPath("$.idUsuario").value(2));
    }

    @Test
    void cambiarPassword_debeRetornarMensajeOk() throws Exception {

        CambiarPasswordDTO dto = new CambiarPasswordDTO();
        dto.setPasswordActual("oldpass");
        dto.setPasswordNueva("newpass123");

        Mockito.doNothing()
                .when(usuarioService)
                .cambiarPassword(1, "oldpass", "newpass123");

        mockMvc.perform(put("/api/usuarios/1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Contraseña actualizada correctamente"));
    }

    @Test
    void cambiarPassword_debeRetornar400SiFalla() throws Exception {

        CambiarPasswordDTO dto = new CambiarPasswordDTO();
        dto.setPasswordActual("incorrecta");
        dto.setPasswordNueva("nuevaPass");

        Mockito.doThrow(new RuntimeException("Contraseña incorrecta"))
                .when(usuarioService)
                .cambiarPassword(1, "incorrecta", "nuevaPass");

        mockMvc.perform(put("/api/usuarios/1/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Contraseña incorrecta"));
    }
}
