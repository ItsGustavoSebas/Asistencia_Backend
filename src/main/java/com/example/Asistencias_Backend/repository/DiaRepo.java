package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Dia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaRepo extends JpaRepository<Dia, Integer> {
    Dia findByName(String name);
}