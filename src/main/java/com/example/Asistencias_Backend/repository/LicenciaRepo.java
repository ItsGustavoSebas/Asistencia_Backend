package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Licencia;
import com.example.Asistencias_Backend.entity.Programacion_Academica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface LicenciaRepo extends JpaRepository<Licencia, Integer> {
    Optional<Licencia> findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualAndProgramacionAcademica(LocalDate inicio, LocalDate fin, Programacion_Academica programacion);
}