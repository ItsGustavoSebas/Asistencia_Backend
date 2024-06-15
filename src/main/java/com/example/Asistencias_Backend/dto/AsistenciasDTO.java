package com.example.Asistencias_Backend.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
public class AsistenciasDTO {
    private int id;
    private String estado;
    private String nombre;
    private LocalDateTime fecha;
    private LocalDateTime hora;
    private String materiaSigla;
    private String grupoName;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    // Constructor, getters y setters
}

