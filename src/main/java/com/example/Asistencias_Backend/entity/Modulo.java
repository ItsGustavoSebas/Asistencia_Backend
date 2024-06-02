package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
        import lombok.Data;

import java.util.List;

@Entity
@Table(name = "modulo")
@Data
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double latitud;
    private double longitud;
    @ManyToOne
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "modulo")
    private List<Aula> aulas;

}