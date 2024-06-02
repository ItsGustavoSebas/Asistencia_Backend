package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "materia")
@Data
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

}