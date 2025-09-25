package com.phonecinema.servicio_contenido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phonecinema.servicio_contenido.model.Contenido;
import com.phonecinema.servicio_contenido.service.ContenidoService;
import com.phonecinema.servicio_contenido.dto.ContenidoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/contenido")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    @PostMapping("/crear")
    public Contenido crearContenido(@RequestBody ContenidoDTO nuevoContenidoDTO) {
        return contenidoService.crearContenido(nuevoContenidoDTO);
    }

    // Endpoint para obtener todo el contenido
    @GetMapping("/todos")
    public List<Contenido> obtenerTodoElContenido() {
        return contenidoService.obtenerTodoElContenido();
    }
}