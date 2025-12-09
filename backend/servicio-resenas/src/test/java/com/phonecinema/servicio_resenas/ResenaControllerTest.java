package com.phonecinema.servicio_resenas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonecinema.servicio_resenas.config.SecurityDisabledConfig;
import com.phonecinema.servicio_resenas.controller.ResenaController;
import com.phonecinema.servicio_resenas.dto.ResenaDTO;
import com.phonecinema.servicio_resenas.service.ResenaService;
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

@WebMvcTest(ResenaController.class)
@Import(SecurityDisabledConfig.class)

class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResenaService resenaService;

    @Autowired
    private ObjectMapper mapper;

    private String toJson(Object o) throws Exception {
        return mapper.writeValueAsString(o);
    }

    @Test
    void crearResena_debeRetornar201ConResenaCreada() throws Exception {

        ResenaDTO dto = new ResenaDTO();
        dto.setMovieId(10L);
        dto.setUserId(1L);
        dto.setRating(5);
        dto.setComment("Muy buena");

        ResenaDTO respuesta = new ResenaDTO();
        respuesta.setId(100L);
        respuesta.setMovieId(10L);
        respuesta.setUserId(1L);
        respuesta.setRating(5);
        respuesta.setComment("Muy buena");
        respuesta.setUserName("Juan Tester");

        Mockito.when(resenaService.crearResena(any(ResenaDTO.class)))
                .thenReturn(respuesta);

        mockMvc.perform(post("/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.movieId").value(10L))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.comment").value("Muy buena"));
    }

    @Test
    void getByMovie_debeRetornarLista() throws Exception {

        ResenaDTO r = new ResenaDTO();
        r.setId(1L);
        r.setMovieId(200L);
        r.setUserId(10L);
        r.setRating(4);
        r.setComment("Excelente");

        Mockito.when(resenaService.getByMovie(200L))
                .thenReturn(List.of(r));

        mockMvc.perform(get("/reviews/movie/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].movieId").value(200L))
                .andExpect(jsonPath("$[0].userId").value(10L))
                .andExpect(jsonPath("$[0].comment").value("Excelente"))
                .andExpect(jsonPath("$[0].rating").value(4));
    }

    @Test
    void getAll_debeRetornarLista() throws Exception {

        ResenaDTO r = new ResenaDTO();
        r.setId(1L);
        r.setComment("Correcta");
        r.setRating(3);

        Mockito.when(resenaService.getAll())
                .thenReturn(List.of(r));

        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].comment").value("Correcta"))
                .andExpect(jsonPath("$[0].rating").value(3));
    }

    @Test
    void delete_debeRetornar204() throws Exception {
        Mockito.doNothing().when(resenaService).delete(5L);

        mockMvc.perform(delete("/reviews/5"))
                .andExpect(status().isNoContent());
    }
}
