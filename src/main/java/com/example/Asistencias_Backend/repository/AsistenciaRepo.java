package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsistenciaRepo extends JpaRepository<Asistencia, Integer> {

}