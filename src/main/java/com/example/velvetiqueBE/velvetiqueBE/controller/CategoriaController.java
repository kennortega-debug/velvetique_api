package com.example.velvetiqueBE.velvetiqueBE.controller;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.CategoriaDto;
import com.example.velvetiqueBE.velvetiqueBE.model.Categoria;
import com.example.velvetiqueBE.velvetiqueBE.service.CategoriaService;
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
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDto> findAll() {
        return categoriaService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public CategoriaDto findById(@PathVariable Long id) {
        return toDto(categoriaService.findById(id)
                .orElseThrow(() -> notFound(id)));
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> create(@Valid @RequestBody CategoriaDto dto) {
        Categoria categoria = toEntity(dto);
        categoria.setIdCategoria(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(categoriaService.save(categoria)));
    }

    @PutMapping("/{id}")
    public CategoriaDto update(@PathVariable Long id, @Valid @RequestBody CategoriaDto dto) {
        if (categoriaService.findById(id).isEmpty()) {
            throw notFound(id);
        }
        Categoria categoria = toEntity(dto);
        categoria.setIdCategoria(id);
        return toDto(categoriaService.save(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (categoriaService.findById(id).isEmpty()) {
            throw notFound(id);
        }
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CategoriaDto toDto(Categoria categoria) {
        return new CategoriaDto(categoria.getIdCategoria(), categoria.getNombre(),
                categoria.getDescripcion(), categoria.getEstado());
    }

    private Categoria toEntity(CategoriaDto dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());
        categoria.setEstado(dto.estado());
        return categoria;
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada: " + id);
    }
}
