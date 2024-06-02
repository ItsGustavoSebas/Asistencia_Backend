package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "materia_carrera")
@Data
public class Materia_Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int semestre;
    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;
}