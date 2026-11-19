package com.example.velvetiqueBE.velvetiqueBE.controller;

import com.example.velvetiqueBE.velvetiqueBE.Dtos.ProductoDto;
import com.example.velvetiqueBE.velvetiqueBE.model.Producto;
import com.example.velvetiqueBE.velvetiqueBE.service.CategoriaService;
import com.example.velvetiqueBE.velvetiqueBE.service.MarcaService;
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
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    public ProductoController(ProductoService productoService, CategoriaService categoriaService,
            MarcaService marcaService) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
    }

    @GetMapping
    public List<ProductoDto> findAll() {
        return productoService.findAll().stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public ProductoDto findById(@PathVariable Long id) {
        return toDto(productoService.findById(id)
                .orElseThrow(() -> notFound("Producto", id)));
    }

    @PostMapping
    public ResponseEntity<ProductoDto> create(@Valid @RequestBody ProductoDto dto) {
        Producto producto = toEntity(dto);
        producto.setIdProducto(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(productoService.save(producto)));
    }

    @PutMapping("/{id}")
    public ProductoDto update(@PathVariable Long id, @Valid @RequestBody ProductoDto dto) {
        if (productoService.findById(id).isEmpty()) {
            throw notFound("Producto", id);
        }
        Producto producto = toEntity(dto);
        producto.setIdProducto(id);
        return toDto(productoService.save(producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productoService.findById(id).isEmpty()) {
            throw notFound("Producto", id);
        }
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private ProductoDto toDto(Producto producto) {
        return new ProductoDto(producto.getIdProducto(), producto.getCodigoSku(), producto.getNombre(),
                producto.getDescripcion(), producto.getPrecio(), producto.getStock(),
                producto.getStockCritico(), producto.getVisible(),
                producto.getCategoria().getIdCategoria(), producto.getMarca().getIdMarca());
    }

    private Producto toEntity(ProductoDto dto) {
        Producto producto = new Producto();
        producto.setCodigoSku(dto.codigoSku());
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());
        producto.setStockCritico(dto.stockCritico());
        producto.setVisible(dto.visible());
        producto.setCategoria(categoriaService.findById(dto.idCategoria())
                .orElseThrow(() -> notFound("Categoria", dto.idCategoria())));
        producto.setMarca(marcaService.findById(dto.idMarca())
                .orElseThrow(() -> notFound("Marca", dto.idMarca())));
        return producto;
    }

    private ResponseStatusException notFound(String entity, Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, entity + " no encontrado: " + id);
    }
}
