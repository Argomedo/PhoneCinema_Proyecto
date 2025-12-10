package com.phonecinema.servicio_resenas;

import com.phonecinema.servicio_resenas.client.UsuarioClient;
import com.phonecinema.servicio_resenas.client.UsuarioResponse;
import com.phonecinema.servicio_resenas.dto.RatingResponse;
import com.phonecinema.servicio_resenas.dto.ResenaDTO;
import com.phonecinema.servicio_resenas.model.Resena;
import com.phonecinema.servicio_resenas.repository.ResenaRepository;
import com.phonecinema.servicio_resenas.service.ResenaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ResenaServiceTest {

    @Mock
    private ResenaRepository repository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private ResenaService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearResena_debeCrearYRetornarDTO_conFotoUsuario() {

        ResenaDTO dto = new ResenaDTO();
        dto.setMovieId(10L);
        dto.setUserId(1L);
        dto.setRating(5);
        dto.setComment("Excelente");

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Juan Tester");
        usuario.setFotoPerfilUrl("foto.png");

        when(usuarioClient.getUsuario(1L)).thenReturn(usuario);

        Resena entity = new Resena();
        entity.setId(100L);
        entity.setMovieId(10L);
        entity.setUserId(1L);
        entity.setUserName("Juan Tester");
        entity.setFotoUsuario("foto.png");
        entity.setRating(5);
        entity.setComment("Excelente");
        entity.setTimestamp(LocalDateTime.now());

        when(repository.save(any(Resena.class))).thenReturn(entity);

        ResenaDTO result = service.crearResena(dto);

        assertThat(result.getId()).isEqualTo(100L);
        assertThat(result.getMovieId()).isEqualTo(10L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getUserName()).isEqualTo("Juan Tester");
        assertThat(result.getRating()).isEqualTo(5);
        assertThat(result.getComment()).isEqualTo("Excelente");
        assertThat(result.getFotoUsuario()).isEqualTo("foto.png");

        verify(repository).save(any(Resena.class));
    }

    @Test
    void crearResena_debeLanzarExcepcionCuandoUsuarioNoExiste() {

        ResenaDTO dto = new ResenaDTO();
        dto.setUserId(5L);

        when(usuarioClient.getUsuario(5L)).thenReturn(null);

        assertThatThrownBy(() -> service.crearResena(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Usuario no encontrado en el microservicio usuarios");

        verify(usuarioClient).getUsuario(5L);
        verify(repository, never()).save(any());
    }

    @Test
    void getByMovie_debeRetornarListaDeDTOs() {

        Resena r = new Resena();
        r.setId(1L);
        r.setMovieId(20L);
        r.setUserId(7L);
        r.setUserName("Tester");
        r.setFotoUsuario("foto-tester.png");
        r.setRating(4);
        r.setComment("Muy buena");
        r.setTimestamp(LocalDateTime.now());

        when(repository.findByMovieId(20L)).thenReturn(List.of(r));

        // mapToDTO ahora llama a usuarioClient.getUsuario(userId)
        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setIdUsuario(7L);
        usuario.setNombre("Tester");
        usuario.setFotoPerfilUrl("foto-tester.png");
        when(usuarioClient.getUsuario(7L)).thenReturn(usuario);

        List<ResenaDTO> result = service.getByMovie(20L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getMovieId()).isEqualTo(20L);
        assertThat(result.get(0).getComment()).isEqualTo("Muy buena");
        assertThat(result.get(0).getFotoUsuario()).isEqualTo("foto-tester.png");

        verify(repository).findByMovieId(20L);
        verify(usuarioClient).getUsuario(7L);
    }

    @Test
    void getAll_debeRetornarLista() {

        Resena r = new Resena();
        r.setId(1L);
        r.setComment("Correcta");
        r.setRating(3);
        r.setMovieId(5L);
        r.setUserId(1L);
        r.setTimestamp(LocalDateTime.now());

        when(repository.findAll()).thenReturn(List.of(r));

        UsuarioResponse usuario = new UsuarioResponse();
        usuario.setIdUsuario(1L);
        usuario.setNombre("User");
        usuario.setFotoPerfilUrl("foto-user.png");
        when(usuarioClient.getUsuario(1L)).thenReturn(usuario);

        List<ResenaDTO> result = service.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getComment()).isEqualTo("Correcta");
        assertThat(result.get(0).getFotoUsuario()).isEqualTo("foto-user.png");

        verify(repository).findAll();
        verify(usuarioClient).getUsuario(1L);
    }

    @Test
    void delete_debeEliminarPorId() {

        doNothing().when(repository).deleteById(10L);

        service.delete(10L);

        verify(repository).deleteById(10L);
    }

    @Test
    void getPromedio_debeRetornarValoresDeRepositorio() {

        when(repository.obtenerPromedio(50L)).thenReturn(4.0);
        when(repository.contarResenas(50L)).thenReturn(8L);

        RatingResponse resp = service.getPromedio(50L);

        assertThat(resp.getPromedio()).isEqualTo(4.0);
        assertThat(resp.getTotalResenas()).isEqualTo(8L);

        verify(repository).obtenerPromedio(50L);
        verify(repository).contarResenas(50L);
    }

    @Test
    void getPromedio_debeRetornarCerosCuandoNull() {

        when(repository.obtenerPromedio(99L)).thenReturn(null);
        when(repository.contarResenas(99L)).thenReturn(null);

        RatingResponse resp = service.getPromedio(99L);

        assertThat(resp.getPromedio()).isEqualTo(0.0);
        assertThat(resp.getTotalResenas()).isEqualTo(0L);

        verify(repository).obtenerPromedio(99L);
        verify(repository).contarResenas(99L);
    }
}
