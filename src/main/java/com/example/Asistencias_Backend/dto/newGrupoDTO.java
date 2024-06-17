package com.example.Asistencias_Backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class newGrupoDTO {
    private int docenteId;
    private int materia_carreraId;
    private int facultad_gestionId;
    private String nombre;
    private List<Dia_HorarioDTO> diaHorarioDTOS;
}
