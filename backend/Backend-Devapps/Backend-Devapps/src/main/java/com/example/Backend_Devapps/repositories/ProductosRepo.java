package com.example.Backend_Devapps.repositories;

import com.example.Backend_Devapps.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductosRepo extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombre(String nombre);
}