package com.phonecinema.servicio_resenas.service;

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

    public ResenaDTO crearResena(ResenaDTO dto) {
        Resena entity = new Resena();
        entity.setMovieId(dto.getMovieId());
        entity.setUserId(dto.getUserId());
        entity.setUserName(dto.getUserName());           // ← agregar
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
        ResenaDTO dto = new ResenaDTO();
        dto.setId(entity.getId());
        dto.setMovieId(entity.getMovieId());
        dto.setUserId(entity.getUserId());
        dto.setUserName(entity.getUserName());         // ← corregido
        dto.setRating(entity.getRating());
        dto.setComment(entity.getComment());
        dto.setTimestamp(entity.getTimestamp());
        return dto;
    }
}

