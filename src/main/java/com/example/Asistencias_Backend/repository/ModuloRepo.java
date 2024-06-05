package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Modulo;
import com.example.Asistencias_Backend.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuloRepo extends JpaRepository<Modulo, Integer> {
    List<Modulo> findByNameContainingIgnoreCase(String name);
}