package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "facultad")
@Data
public class Facultad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

}
