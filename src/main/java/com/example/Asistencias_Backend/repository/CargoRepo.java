package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepo extends JpaRepository<Cargo, Integer> {
}