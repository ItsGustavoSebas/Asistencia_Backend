
package com.example.Asistencias_Backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
        import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "horario")
@Data
@JsonIgnoreProperties({"diaHorarios"})
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    @OneToMany(mappedBy = "horario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dia_Horario> diaHorarios;
}