package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}
