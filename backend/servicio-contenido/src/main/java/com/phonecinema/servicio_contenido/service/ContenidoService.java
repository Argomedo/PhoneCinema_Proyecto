package com.phonecinema.servicio_contenido.service;

import java.util.List;
import com.phonecinema.servicio_contenido.dto.ContenidoDTO;
import com.phonecinema.servicio_contenido.model.Contenido;

public interface ContenidoService {
    Contenido crearContenido(ContenidoDTO contenidoDTO);
    
    List<Contenido> obtenerTodoElContenido();
    
}