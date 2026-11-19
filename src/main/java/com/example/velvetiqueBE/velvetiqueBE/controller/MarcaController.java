package com.example.velvetiqueBE.velvetiqueBE.controller;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.MarcaDto;
import com.example.velvetiqueBE.velvetiqueBE.model.Marca;
import com.example.velvetiqueBE.velvetiqueBE.service.MarcaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping
    public List<MarcaDto> findAll() {
        return marcaService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public MarcaDto findById(@PathVariable Long id) {
        return toDto(marcaService.findById(id)
                .orElseThrow(() -> notFound(id)));
    }

    @PostMapping
    public ResponseEntity<MarcaDto> create(@Valid @RequestBody MarcaDto dto) {
        Marca marca = toEntity(dto);
        marca.setIdMarca(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(marcaService.save(marca)));
    }

    @PutMapping("/{id}")
    public MarcaDto update(@PathVariable Long id, @Valid @RequestBody MarcaDto dto) {
        if (marcaService.findById(id).isEmpty()) {
            throw notFound(id);
        }
        Marca marca = toEntity(dto);
        marca.setIdMarca(id);
        return toDto(marcaService.save(marca));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (marcaService.findById(id).isEmpty()) {
            throw notFound(id);
        }
        marcaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MarcaDto toDto(Marca marca) {
        return new MarcaDto(marca.getIdMarca(), marca.getNombre(), marca.getDescripcion(), marca.getEstado());
    }

    private Marca toEntity(MarcaDto dto) {
        Marca marca = new Marca();
        marca.setNombre(dto.nombre());
        marca.setDescripcion(dto.descripcion());
        marca.setEstado(dto.estado());
        return marca;
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada: " + id);
    }
}
