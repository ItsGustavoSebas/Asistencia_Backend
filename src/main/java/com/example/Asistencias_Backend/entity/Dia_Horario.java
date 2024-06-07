package com.example.Asistencias_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
        import lombok.Data;

import java.util.List;

@Entity
@Table(name = "dia_horario")
@Data
@JsonIgnoreProperties({"programacionAcademicas"})
public class Dia_Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "dia_id")
    private Dia dia;

    @ManyToOne
    @JoinColumn(name = "horario_id")
    private Horario horario;

    @OneToMany(mappedBy = "diaHorario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Programacion_Academica> programacionAcademicas;
}