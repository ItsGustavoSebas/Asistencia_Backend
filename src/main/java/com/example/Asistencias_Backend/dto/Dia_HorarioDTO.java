package com.example.Asistencias_Backend.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class Dia_HorarioDTO {
    private Integer programacionId;
    private Integer moduloId;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String modulo;
    private int aula;
}
