package com.example.Asistencias_Backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FechaDTO {
    private int gestionId;
    private int tipoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;

    // Constructor, getters y setters
}