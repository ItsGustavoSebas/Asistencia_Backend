package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
        import lombok.Data;

@Entity
@Table(name = "aula")
@Data
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

}