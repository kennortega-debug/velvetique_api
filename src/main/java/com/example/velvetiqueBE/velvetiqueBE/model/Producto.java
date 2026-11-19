package com.example.velvetiqueBE.velvetiqueBE.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto",
        uniqueConstraints = @UniqueConstraint(name = "uk_producto_codigo_sku", columnNames = "codigo_sku"),
        indexes = {
                @Index(name = "idx_producto_nombre", columnList = "nombre"),
                @Index(name = "idx_producto_categoria", columnList = "id_categoria")
        })
@Getter
@Setter
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "codigo_sku", nullable = false, length = 50)
    private String codigoSku;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock", nullable = false)
    private Integer stock = 0;

    @Column(name = "stock_critico", nullable = false)
    private Integer stockCritico = 5;

    @Column(name = "visible", nullable = false)
    private Boolean visible = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_categoria", nullable = false, foreignKey = @jakarta.persistence.ForeignKey(name = "fk_producto_categoria"))
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_marca", nullable = false, foreignKey = @jakarta.persistence.ForeignKey(name = "fk_producto_marca"))
    private Marca marca;

    @OneToMany(mappedBy = "producto")
    private List<ImagenProducto> imagenes = new ArrayList<>();

    @OneToMany(mappedBy = "producto")
    private List<DetalleCarrito> detallesCarrito = new ArrayList<>();
}
