package com.example.Asistencias_Backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class LicenciaDTO {
    private int id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String motivo;
    private Boolean aprobado;

    // Constructor, getters y setters
}