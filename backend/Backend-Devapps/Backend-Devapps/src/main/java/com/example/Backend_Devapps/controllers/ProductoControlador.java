package com.example.Backend_Devapps.controllers;

import com.example.Backend_Devapps.entity.Producto;
import com.example.Backend_Devapps.services.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    // Endpoint para obtener todos los productos (GET /productos)
    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoServicio.obtenerTodos();
    }

    // Endpoint para obtener un producto por ID (GET /productos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoServicio.obtenerPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para crear un nuevo producto (POST /productos)
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoServicio.crear(producto);
    }

    // Endpoint para actualizar un producto (PUT /productos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoServicio.actualizar(id, producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar un producto (DELETE /productos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}