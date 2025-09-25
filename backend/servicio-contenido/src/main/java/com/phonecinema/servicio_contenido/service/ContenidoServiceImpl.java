package com.phonecinema.servicio_contenido.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phonecinema.servicio_contenido.dto.ContenidoDTO;
import com.phonecinema.servicio_contenido.model.Contenido;
import com.phonecinema.servicio_contenido.repository.ContenidoRepository;
import java.util.List;

@Service
public class ContenidoServiceImpl implements ContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;

    @Override
    public Contenido crearContenido(ContenidoDTO contenidoDTO) {
        // Convertimos el DTO a una Entidad Contenido
        Contenido nuevoContenido = new Contenido();
        nuevoContenido.setTitulo(contenidoDTO.getTitulo());
        nuevoContenido.setSinopsis(contenidoDTO.getSinopsis());
        nuevoContenido.setPortadaUrl(contenidoDTO.getPortadaUrl());
        nuevoContenido.setBannerUrl(contenidoDTO.getBannerUrl());
        nuevoContenido.setAnoEstreno(contenidoDTO.getAnoEstreno());
        nuevoContenido.setCategoria(contenidoDTO.getCategoria());
        nuevoContenido.setTipo(contenidoDTO.getTipo());

        return contenidoRepository.save(nuevoContenido);
    }

    @Override
    public List<Contenido> obtenerTodoElContenido() {
        // findAll() es un método que JpaRepository nos regala
        return contenidoRepository.findAll();
    }
}