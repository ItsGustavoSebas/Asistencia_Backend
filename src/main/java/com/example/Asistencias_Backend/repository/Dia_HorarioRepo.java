package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Dia_Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface Dia_HorarioRepo extends JpaRepository<Dia_Horario, Integer> {
    Dia_Horario findByDia_NameAndHorario_HoraInicioAndHorario_HoraFin(String name, LocalTime horaInicio, LocalTime horaFin);
}
