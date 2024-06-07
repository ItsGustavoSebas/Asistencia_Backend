package com.example.Asistencias_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "materia_carrera")
@Data
@JsonIgnoreProperties({"grupos"})
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

    @OneToMany(mappedBy = "materiaCarrera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> grupos;
}