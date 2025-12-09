package com.phonecinema.serviciospeliculas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phonecinema.serviciospeliculas.controller.PeliculaController;
import com.phonecinema.serviciospeliculas.dto.PeliculaCreateDTO;
import com.phonecinema.serviciospeliculas.dto.PeliculaDTO;
import com.phonecinema.serviciospeliculas.dto.PeliculaupdateDTO;
import com.phonecinema.serviciospeliculas.service.PeliculaService;

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

@WebMvcTest(PeliculaController.class)  
class PeliculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PeliculaService peliculaService;

    @Autowired
    private ObjectMapper mapper;

    private String toJson(Object o) throws Exception {
        return mapper.writeValueAsString(o);
    }

    // ---------------------------------------------------------------------
    // GET ALL
    // ---------------------------------------------------------------------
    @Test
    void getAll_debeRetornarListaDePeliculas() throws Exception {

        PeliculaDTO p = new PeliculaDTO(
            1L,
            "Matrix",
            "Un hacker descubre la verdad",
            "Acción",
            "2h 16m",
            1999,
            "urlMatrix"
        );

        Mockito.when(peliculaService.getAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/peliculas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Matrix"))
                .andExpect(jsonPath("$[0].descripcion").value("Un hacker descubre la verdad"))
                .andExpect(jsonPath("$[0].genero").value("Acción"))
                .andExpect(jsonPath("$[0].duracion").value("2h 16m"))
                .andExpect(jsonPath("$[0].anio").value(1999))
                .andExpect(jsonPath("$[0].posterUrl").value("urlMatrix"));
    }

    // ---------------------------------------------------------------------
    // GET BY ID
    // ---------------------------------------------------------------------
    @Test
    void getById_debeRetornarPelicula() throws Exception {

        PeliculaDTO p = new PeliculaDTO(
            1L,
            "Matrix",
            "Sci-fi revolucionaria",
            "Acción",
            "2h 16m",
            1999,
            "urlMatrix"
        );

        Mockito.when(peliculaService.getById(1L)).thenReturn(p);

        mockMvc.perform(get("/api/peliculas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Matrix"));
    }

    // ---------------------------------------------------------------------
    // GET BY GENERO
    // ---------------------------------------------------------------------
    @Test
    void getByGenero_debeRetornarLista() throws Exception {

        PeliculaDTO p = new PeliculaDTO(
            1L,
            "John Wick",
            "Acción intensa",
            "Acción",
            "1h 41m",
            2014,
            "urlJW"
        );

        Mockito.when(peliculaService.getByGenero("Acción"))
                .thenReturn(List.of(p));

        mockMvc.perform(get("/api/peliculas/genero/Acción"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].genero").value("Acción"))
                .andExpect(jsonPath("$[0].nombre").value("John Wick"));
    }

    // ---------------------------------------------------------------------
    // CREATE (POST)
    // ---------------------------------------------------------------------
    @Test
    void create_debeRetornarPeliculaCreada() throws Exception {

        PeliculaCreateDTO dto = new PeliculaCreateDTO();
        dto.setNombre("Avatar");
        dto.setDescripcion("Planeta Pandora");
        dto.setGenero("Sci-Fi");
        dto.setDuracion("2h 42m");
        dto.setAnio(2009);
        dto.setPosterUrl("urlAvatar");

        PeliculaDTO result = new PeliculaDTO(
            100L,
            "Avatar",
            "Planeta Pandora",
            "Sci-Fi",
            "2h 42m",
            2009,
            "urlAvatar"
        );

        Mockito.when(peliculaService.create(any(PeliculaCreateDTO.class)))
                .thenReturn(result);

        mockMvc.perform(post("/api/peliculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(dto)))
                .andExpect(status().isOk())   // tu controller retorna 200 OK
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.nombre").value("Avatar"))
                .andExpect(jsonPath("$.genero").value("Sci-Fi"));
    }

    // ---------------------------------------------------------------------
    // UPDATE (PUT)
    // ---------------------------------------------------------------------
    @Test
    void update_debeRetornarPeliculaActualizada() throws Exception {

        PeliculaupdateDTO dto = new PeliculaupdateDTO(
            "Avatar 2",
            "Nueva historia",
            "Sci-Fi",
            "3h 10m",
            2022,
            "urlA2"
        );

        PeliculaDTO updated = new PeliculaDTO(
            1L,
            "Avatar 2",
            "Nueva historia",
            "Sci-Fi",
            "3h 10m",
            2022,
            "urlA2"
        );

        Mockito.when(peliculaService.update(eq(1L), any(PeliculaupdateDTO.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/peliculas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Avatar 2"))
                .andExpect(jsonPath("$.anio").value(2022));
    }

    // ---------------------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------------------
    @Test
    void delete_debeRetornar200() throws Exception {

        Mockito.doNothing().when(peliculaService).delete(1L);

        mockMvc.perform(delete("/api/peliculas/1"))
                .andExpect(status().isOk());
    }
}
