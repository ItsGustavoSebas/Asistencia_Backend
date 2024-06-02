package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Aula;
import com.example.Asistencias_Backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepo extends JpaRepository<Aula, Integer> {
    Aula findByName(String name);
}