package com.ejemplo.serviciofeedback.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "servicio-usuario", url = "http://localhost:8081/api/usuarios")
public interface UsuarioClient {

    @GetMapping("/{id}")
    UsuarioResponse getUsuario(@PathVariable("id") Long id);
}
