package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "carrera")
@Data
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

}