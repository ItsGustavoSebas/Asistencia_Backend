
package com.example.Asistencias_Backend.entity;

import jakarta.persistence.*;
        import lombok.Data;

@Entity
@Table(name = "horario")
@Data
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String hora_inicio;
    private String hora_fin;

}