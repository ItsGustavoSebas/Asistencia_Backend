package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Materia;
import com.example.Asistencias_Backend.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaRepo extends JpaRepository<Materia, Integer> {
    List<Materia> findByNameContainingIgnoreCase(String name);
}