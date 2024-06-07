package com.example.Asistencias_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "gestion")
@Data
@JsonIgnoreProperties({"facultadGestions"})
public class Gestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "gestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Facultad_Gestion> facultadGestions;
}