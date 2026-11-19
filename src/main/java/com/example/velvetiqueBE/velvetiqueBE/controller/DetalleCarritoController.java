package com.example.velvetiqueBE.velvetiqueBE.controller;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.DetalleCarritoDto;
import com.example.velvetiqueBE.velvetiqueBE.model.DetalleCarrito;
import com.example.velvetiqueBE.velvetiqueBE.service.CarritoService;
import com.example.velvetiqueBE.velvetiqueBE.service.DetalleCarritoService;
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
@RequestMapping("/api/detalles-carrito")
public class DetalleCarritoController {

    private final DetalleCarritoService detalleCarritoService;
    private final CarritoService carritoService;
    private final ProductoService productoService;

    public DetalleCarritoController(DetalleCarritoService detalleCarritoService,
            CarritoService carritoService, ProductoService productoService) {
        this.detalleCarritoService = detalleCarritoService;
        this.carritoService = carritoService;
        this.productoService = productoService;
    }

    @GetMapping
    public List<DetalleCarritoDto> findAll() {
        return detalleCarritoService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public DetalleCarritoDto findById(@PathVariable Long id) {
        return toDto(detalleCarritoService.findById(id)
                .orElseThrow(() -> notFound("DetalleCarrito", id)));
    }

    @PostMapping
    public ResponseEntity<DetalleCarritoDto> create(@Valid @RequestBody DetalleCarritoDto dto) {
        DetalleCarrito detalle = toEntity(dto);
        detalle.setIdDetalleCarrito(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(detalleCarritoService.save(detalle)));
    }

    @PutMapping("/{id}")
    public DetalleCarritoDto update(@PathVariable Long id, @Valid @RequestBody DetalleCarritoDto dto) {
        if (detalleCarritoService.findById(id).isEmpty()) {
            throw notFound("DetalleCarrito", id);
        }
        DetalleCarrito detalle = toEntity(dto);
        detalle.setIdDetalleCarrito(id);
        return toDto(detalleCarritoService.save(detalle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (detalleCarritoService.findById(id).isEmpty()) {
            throw notFound("DetalleCarrito", id);
        }
        detalleCarritoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private DetalleCarritoDto toDto(DetalleCarrito detalle) {
        return new DetalleCarritoDto(detalle.getIdDetalleCarrito(), detalle.getCarrito().getIdCarrito(),
                detalle.getProducto().getIdProducto(), detalle.getCantidad(),
                detalle.getPrecioUnitario(), detalle.getSubtotal());
    }

    private DetalleCarrito toEntity(DetalleCarritoDto dto) {
        DetalleCarrito detalle = new DetalleCarrito();
        detalle.setCarrito(carritoService.findById(dto.idCarrito())
                .orElseThrow(() -> notFound("Carrito", dto.idCarrito())));
        detalle.setProducto(productoService.findById(dto.idProducto())
                .orElseThrow(() -> notFound("Producto", dto.idProducto())));
        detalle.setCantidad(dto.cantidad());
        detalle.setPrecioUnitario(dto.precioUnitario());
        return detalle;
    }

    private ResponseStatusException notFound(String entity, Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, entity + " no encontrado: " + id);
    }
}
