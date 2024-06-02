package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Carrera;
import com.example.Asistencias_Backend.entity.Materia_Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Materia_CarreraRepo extends JpaRepository<Materia_Carrera, Integer> {
    List<Materia_Carrera> findByCarrera(Carrera carrera);
}
