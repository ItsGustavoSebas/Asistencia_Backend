package com.example.Asistencias_Backend.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "programacion_academica")
@Data
public class Programacion_Academica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer aula;

    @ManyToOne
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "dia_horario_id")
    private Dia_Horario diaHorario;

    @OneToMany(mappedBy = "programacionAcademica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asistencia> asistencias;

}