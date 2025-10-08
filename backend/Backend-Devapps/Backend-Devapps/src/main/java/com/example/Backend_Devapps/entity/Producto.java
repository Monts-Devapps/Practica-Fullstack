package com.example.Backend_Devapps.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String marca;

    private String categoria;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer existencias;

    private boolean activo = true;
}