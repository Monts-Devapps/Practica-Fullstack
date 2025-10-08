package com.example.Backend_Devapps.services;

import com.example.Backend_Devapps.entity.Producto;
import com.example.Backend_Devapps.repositories.ProductosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private ProductosRepo productosRepo;

    public List<Producto> obtenerTodos() {
        return productosRepo.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productosRepo.findById(id);
    }

    public Producto crear(Producto producto) {
        if (productosRepo.findByNombre(producto.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + producto.getNombre());
        }
        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        if (producto.getExistencias() < 0) {
            throw new IllegalArgumentException("Las existencias no pueden ser negativas.");
        }
        return productosRepo.save(producto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        return productosRepo.findById(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(productoActualizado.getNombre());
                    productoExistente.setMarca(productoActualizado.getMarca());
                    productoExistente.setCategoria(productoActualizado.getCategoria());
                    productoExistente.setPrecio(productoActualizado.getPrecio());
                    productoExistente.setExistencias(productoActualizado.getExistencias());
                    return productosRepo.save(productoExistente);
                }).orElseThrow(() -> new RuntimeException("Producto no encontrado con el id " + id));
    }

    public void eliminar(Long id) {
        productosRepo.deleteById(id);
    }




    public Producto cambiarEstadoActivo(Long id) {
        return productosRepo.findById(id)
                .map(producto -> {
                    producto.setActivo(!producto.isActivo()); // Invierte el valor booleano
                    return productosRepo.save(producto);
                }).orElseThrow(() -> new RuntimeException("Producto no encontrado con el id " + id));
    }


    public Producto ajustarExistencias(Long id, int cantidad) {
        return productosRepo.findById(id)
                .map(producto -> {
                    int nuevasExistencias = producto.getExistencias() + cantidad;
                    if (nuevasExistencias < 0) {
                        throw new IllegalArgumentException("El ajuste no puede resultar en existencias negativas.");
                    }
                    producto.setExistencias(nuevasExistencias);
                    return productosRepo.save(producto);
                }).orElseThrow(() -> new RuntimeException("Producto no encontrado con el id " + id));
    }
}