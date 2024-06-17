package com.example.Asistencias_Backend.dto;
import lombok.Data;
import java.util.List;

@Data
public class GrupoDTO {
    private int grupoId;
    private int materia_carreraId;
    private String siglas;
    private String nombre;
    private String materia;
    private int semestre;
    private String docente;
    private List<Dia_HorarioDTO> diaHorarioDTOS;
}
