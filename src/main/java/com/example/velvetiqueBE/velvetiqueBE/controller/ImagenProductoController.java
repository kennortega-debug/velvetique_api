package com.example.velvetiqueBE.velvetiqueBE.controller;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.ImagenProductoDto;
import com.example.velvetiqueBE.velvetiqueBE.model.ImagenProducto;
import com.example.velvetiqueBE.velvetiqueBE.service.ImagenProductoService;
import com.example.velvetiqueBE.velvetiqueBE.service.ProductoService;
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
@RequestMapping("/api/imagenes-producto")
public class ImagenProductoController {

    private final ImagenProductoService imagenProductoService;
    private final ProductoService productoService;

    public ImagenProductoController(ImagenProductoService imagenProductoService, ProductoService productoService) {
        this.imagenProductoService = imagenProductoService;
        this.productoService = productoService;
    }

    @GetMapping
    public List<ImagenProductoDto> findAll() {
        return imagenProductoService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ImagenProductoDto findById(@PathVariable Long id) {
        return toDto(imagenProductoService.findById(id)
                .orElseThrow(() -> notFound("ImagenProducto", id)));
    }

    @PostMapping
    public ResponseEntity<ImagenProductoDto> create(@Valid @RequestBody ImagenProductoDto dto) {
        ImagenProducto imagen = toEntity(dto);
        imagen.setIdImagen(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(imagenProductoService.save(imagen)));
    }

    @PutMapping("/{id}")
    public ImagenProductoDto update(@PathVariable Long id, @Valid @RequestBody ImagenProductoDto dto) {
        if (imagenProductoService.findById(id).isEmpty()) {
            throw notFound("ImagenProducto", id);
        }
        ImagenProducto imagen = toEntity(dto);
        imagen.setIdImagen(id);
        return toDto(imagenProductoService.save(imagen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (imagenProductoService.findById(id).isEmpty()) {
            throw notFound("ImagenProducto", id);
        }
        imagenProductoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ImagenProductoDto toDto(ImagenProducto imagen) {
        return new ImagenProductoDto(imagen.getIdImagen(), imagen.getProducto().getIdProducto(),
                imagen.getUrlImagen(), imagen.getEsPrincipal(), imagen.getOrden());
    }

    private ImagenProducto toEntity(ImagenProductoDto dto) {
        ImagenProducto imagen = new ImagenProducto();
        imagen.setProducto(productoService.findById(dto.idProducto())
                .orElseThrow(() -> notFound("Producto", dto.idProducto())));
        imagen.setUrlImagen(dto.urlImagen());
        imagen.setEsPrincipal(dto.esPrincipal());
        imagen.setOrden(dto.orden());
        return imagen;
    }

    private ResponseStatusException notFound(String entity, Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, entity + " no encontrado: " + id);
    }
}
