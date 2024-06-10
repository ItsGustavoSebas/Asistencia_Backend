package com.example.Asistencias_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "asistencia")
@Data
@JsonIgnoreProperties({"programacionAcademica"})
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime fecha;
    private Double latitud;
    private Double longitud;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "programacion_academica_id")
    private Programacion_Academica programacionAcademica;

}