package com.phonecinema.servicio_resenas.service;

import com.phonecinema.servicio_resenas.client.UsuarioClient;
import com.phonecinema.servicio_resenas.client.UsuarioResponse;
import com.phonecinema.servicio_resenas.dto.RatingResponse;
import com.phonecinema.servicio_resenas.dto.ResenaDTO;
import com.phonecinema.servicio_resenas.model.Resena;
import com.phonecinema.servicio_resenas.repository.ResenaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResenaService {

    private final ResenaRepository repository;
    private final UsuarioClient usuarioClient;

    public ResenaDTO crearResena(ResenaDTO dto) {

        UsuarioResponse usuario = usuarioClient.getUsuario(dto.getUserId());

        if (usuario == null || usuario.getIdUsuario() == null) {
            throw new RuntimeException("Usuario no encontrado en el microservicio usuarios");
        }

        Resena entity = new Resena();
        entity.setMovieId(dto.getMovieId());
        entity.setUserId(usuario.getIdUsuario());
        entity.setUserName(usuario.getNombre());
        entity.setFotoUsuario(usuario.getFotoPerfilUrl());   // ← AGREGADO
        entity.setRating(dto.getRating());
        entity.setComment(dto.getComment());
        entity.setTimestamp(LocalDateTime.now());

        entity = repository.save(entity);
        return mapToDTO(entity);
    }

    public List<ResenaDTO> getByMovie(Long movieId) {
        return repository.findByMovieId(movieId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ResenaDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ResenaDTO mapToDTO(Resena entity) {

        UsuarioResponse usuario = usuarioClient.getUsuario(entity.getUserId());

        ResenaDTO dto = new ResenaDTO();
        dto.setId(entity.getId());
        dto.setMovieId(entity.getMovieId());
        dto.setUserId(entity.getUserId());
        dto.setUserName(entity.getUserName());
        dto.setRating(entity.getRating());
        dto.setComment(entity.getComment());
        dto.setTimestamp(entity.getTimestamp());

        // FOTO desde microservicio usuario
        dto.setFotoUsuario(
            usuario != null ? usuario.getFotoPerfilUrl() : null
        );

        return dto;
    }

    public RatingResponse getPromedio(Long movieId) {
        Double promedio = repository.obtenerPromedio(movieId);
        Long total = repository.contarResenas(movieId);

        return new RatingResponse(
            promedio != null ? promedio : 0.0,
            total != null ? total : 0L
        );
    }
}

