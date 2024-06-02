package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
        import lombok.Data;

@Entity
@Table(name = "dia_horario")
@Data
public class Dia_Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "dia_id")
    private Dia dia;

    @ManyToOne
    @JoinColumn(name = "horario_id")
    private Horario horario;
}