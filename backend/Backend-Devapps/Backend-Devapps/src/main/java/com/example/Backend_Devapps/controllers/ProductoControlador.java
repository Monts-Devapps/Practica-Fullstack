package com.example.Backend_Devapps.controllers;

import com.example.Backend_Devapps.entity.Producto;
import com.example.Backend_Devapps.services.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map; // <-- ASEGÚRATE DE AGREGAR ESTA IMPORTACIÓN
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    // Endpoint para obtener los productos
    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoServicio.obtenerTodos();
    }

    // Endpoint para obtener id
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoServicio.obtenerPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para crear productos
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoServicio.crear(producto);
    }

    // Endpoint para actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        try {
            Producto productoActualizado = productoServicio.actualizar(id, producto);
            return ResponseEntity.ok(productoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoServicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Producto> cambiarEstadoActivo(@PathVariable Long id) {
        try {
            Producto producto = productoServicio.cambiarEstadoActivo(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/ajustar")
    public ResponseEntity<Producto> ajustarExistencias(@PathVariable Long id, @RequestBody Map<String, Integer> body) {
        try {
            Integer cantidad = body.get("cantidad");
            if (cantidad == null) {
                return ResponseEntity.badRequest().build();
            }
            Producto producto = productoServicio.ajustarExistencias(id, cantidad);
            return ResponseEntity.ok(producto);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();
        }
    }
}