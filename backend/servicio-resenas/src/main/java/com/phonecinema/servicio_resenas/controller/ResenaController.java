package com.phonecinema.servicio_resenas.controller;

import com.phonecinema.servicio_resenas.dto.RatingResponse;
import com.phonecinema.servicio_resenas.dto.ResenaDTO;
import com.phonecinema.servicio_resenas.service.ResenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @PostMapping
    public ResponseEntity<ResenaDTO> crearResena(@RequestBody @Valid ResenaDTO dto) {
        ResenaDTO respuesta = resenaService.crearResena(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<List<ResenaDTO>> getByMovie(@PathVariable("id") Long movieId) {
        return ResponseEntity.ok(resenaService.getByMovie(movieId));
    }

    @GetMapping
    public ResponseEntity<List<ResenaDTO>> getAll() {
        return ResponseEntity.ok(resenaService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        resenaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/promedio/{movieId}")
    public ResponseEntity<RatingResponse> getPromedio(@PathVariable Long movieId) {
        return ResponseEntity.ok(resenaService.getPromedio(movieId));
}

}
