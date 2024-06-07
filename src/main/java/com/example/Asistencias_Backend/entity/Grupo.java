package com.example.Asistencias_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "grupo")
@Data
@JsonIgnoreProperties({"programacionAcademicas"})
public class Grupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "materia_carrera_id")
    private Materia_Carrera materiaCarrera;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private OurUsers docente;

    @ManyToOne
    @JoinColumn(name = "facultad_gestion_id")
    private Facultad_Gestion facultadGestion;

    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Programacion_Academica> programacionAcademicas;
}