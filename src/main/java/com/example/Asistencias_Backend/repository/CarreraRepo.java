package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarreraRepo extends JpaRepository<Carrera, Integer> {
    List<Carrera> findByNameContainingIgnoreCase(String name);
}
