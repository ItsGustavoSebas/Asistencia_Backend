package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepo extends JpaRepository<Materia, Integer> {
}