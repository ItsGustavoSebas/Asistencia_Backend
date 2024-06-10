package com.example.Asistencias_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "facultad_gestion")
@Data
@JsonIgnoreProperties({"grupos"})
public class Facultad_Gestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "gestion_id")
    private Gestion gestion;

    @ManyToOne
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "facultadGestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FechaImportante> fechaImportantes;

    @OneToMany(mappedBy = "facultadGestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> grupos;

}