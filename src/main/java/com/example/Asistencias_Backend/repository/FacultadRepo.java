package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Facultad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacultadRepo extends JpaRepository<Facultad, Integer> {
    List<Facultad> findByNameContainingIgnoreCase(String name);
}