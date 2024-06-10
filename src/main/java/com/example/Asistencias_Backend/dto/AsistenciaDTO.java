package com.example.Asistencias_Backend.dto;

import com.example.Asistencias_Backend.entity.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AsistenciaDTO {
    private int id;
    private LocalDateTime fecha;
    private Double latitud;
    private Double longitud;
    private String estado;
    private Programacion_AcademicaDTO programacionAcademica;

    public AsistenciaDTO(Asistencia asistencia, Programacion_Academica programacionAcademica) {
        this.id = asistencia.getId();
        this.fecha = asistencia.getFecha();
        this.latitud = asistencia.getLatitud();
        this.longitud = asistencia.getLongitud();
        this.estado = asistencia.getEstado();
        this.programacionAcademica = new Programacion_AcademicaDTO(programacionAcademica);
    }
}

