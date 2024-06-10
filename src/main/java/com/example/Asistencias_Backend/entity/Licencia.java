package com.example.Asistencias_Backend.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
@Entity
@Table(name = "licencia")
@Data
@JsonIgnoreProperties({"programacionAcademica"})
public class Licencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String motivo;
    private Boolean aprobado;
    @ManyToOne
    @JoinColumn(name = "programacion_academica_id")
    private Programacion_Academica programacionAcademica;
}