package com.phonecinema.serviciospeliculas;

import com.phonecinema.serviciospeliculas.dto.PeliculaCreateDTO;
import com.phonecinema.serviciospeliculas.dto.PeliculaDTO;
import com.phonecinema.serviciospeliculas.dto.PeliculaupdateDTO;
import com.phonecinema.serviciospeliculas.model.Pelicula;
import com.phonecinema.serviciospeliculas.repository.PeliculaRepository;
import com.phonecinema.serviciospeliculas.service.PeliculaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PeliculaServiceTest {

    @Mock
    private PeliculaRepository repo;

    @InjectMocks
    private PeliculaService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------------------------------------------------------
    // GET ALL
    // -------------------------------------------------------------------
    @Test
    void getAll_debeRetornarListaMapeada() {

        Pelicula p1 = new Pelicula(
                1L, "Matrix", "Desc 1", "Accion", "2h", 1999, "url1"
        );

        Pelicula p2 = new Pelicula(
                2L, "Joker", "Desc 2", "Drama", "2h 2m", 2019, "url2"
        );

        when(repo.findAll()).thenReturn(List.of(p1, p2));

        List<PeliculaDTO> result = service.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNombre()).isEqualTo("Matrix");
        assertThat(result.get(1).getGenero()).isEqualTo("Drama");

        verify(repo).findAll();
    }

    // -------------------------------------------------------------------
    // GET BY ID
    // -------------------------------------------------------------------
    @Test
    void getById_cuandoExisteDebeRetornarDTO() {

        Pelicula p = new Pelicula(
                1L, "Matrix", "Desc 1", "Accion", "2h", 1999, "url1"
        );

        when(repo.findById(1L)).thenReturn(Optional.of(p));

        PeliculaDTO result = service.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNombre()).isEqualTo("Matrix");

        verify(repo).findById(1L);
    }

    @Test
    void getById_cuandoNoExisteDebeLanzarExcepcion() {

        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Película no encontrada");

        verify(repo).findById(1L);
    }

    // -------------------------------------------------------------------
    // GET BY GENERO
    // -------------------------------------------------------------------
    @Test
    void getByGenero_debeRetornarListaFiltrada() {

        Pelicula p = new Pelicula(
                1L, "John Wick", "Desc JW", "Accion", "1h 41m", 2014, "urlJW"
        );

        when(repo.findByGeneroIgnoreCase("Accion")).thenReturn(List.of(p));

        List<PeliculaDTO> result = service.getByGenero("Accion");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getGenero()).isEqualTo("Accion");

        verify(repo).findByGeneroIgnoreCase("Accion");
    }

    // -------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------
    @Test
    void create_debeGuardarYRetornarDTOCompleto() {

        // DTO de entrada
        PeliculaCreateDTO dto = new PeliculaCreateDTO();
        dto.setNombre("Nueva");
        dto.setDescripcion("Desc nueva");
        dto.setGenero("Accion");
        dto.setDuracion("1h 30m");
        dto.setAnio(2024);
        dto.setPosterUrl("url-nueva");

        // Entidad que "simula" lo que JPA devuelve luego del save()
        Pelicula guardada = new Pelicula(
                10L, "Nueva", "Desc nueva", "Accion", "1h 30m", 2024, "url-nueva"
        );

        // El mock modifica p y retorna guardada
        when(repo.save(any(Pelicula.class))).thenReturn(guardada);

        PeliculaDTO result = service.create(dto);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getNombre()).isEqualTo("Nueva");
        assertThat(result.getGenero()).isEqualTo("Accion");

        verify(repo).save(any(Pelicula.class));
    }

    // -------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------
    @Test
    void update_debeActualizarCorrectamente() {

        Pelicula original = new Pelicula(
                1L, "Matrix", "Desc original", "Accion", "2h", 1999, "url1"
        );

        PeliculaupdateDTO dto = new PeliculaupdateDTO(
                "Matrix Reloaded", "Desc nueva", "Accion", "2h 10m", 2003, "url2"
        );

        // El repo devuelve la película original
        when(repo.findById(1L)).thenReturn(Optional.of(original));

        // El repo devuelve la película actualizada después del save
        when(repo.save(any(Pelicula.class))).thenAnswer(inv -> inv.getArgument(0));

        PeliculaDTO result = service.update(1L, dto);

        assertThat(result.getNombre()).isEqualTo("Matrix Reloaded");
        assertThat(result.getDescripcion()).isEqualTo("Desc nueva");
        assertThat(result.getAnio()).isEqualTo(2003);

        verify(repo).findById(1L);
        verify(repo).save(any(Pelicula.class));
    }

    @Test
    void update_cuandoNoExisteDebeLanzarExcepcion() {

        when(repo.findById(1L)).thenReturn(Optional.empty());

        PeliculaupdateDTO dto = new PeliculaupdateDTO(
                "X", "Y", "Z", "1h", 2000, "url"
        );

        assertThatThrownBy(() -> service.update(1L, dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Película no encontrada");

        verify(repo).findById(1L);
        verify(repo, never()).save(any());
    }

    // -------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------
    @Test
    void delete_debeEliminarCuandoExiste() {

        when(repo.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repo).existsById(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void delete_cuandoNoExisteDebeLanzarExcepcion() {

        when(repo.existsById(1L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Película no encontrada");

        verify(repo).existsById(1L);
        verify(repo, never()).deleteById(anyLong());
    }
}
