package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
        import lombok.Data;

@Entity
@Table(name = "dia")
@Data
public class Dia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

}