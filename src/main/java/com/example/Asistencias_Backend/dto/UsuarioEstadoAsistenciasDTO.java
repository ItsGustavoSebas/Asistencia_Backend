package com.example.Asistencias_Backend.dto;

import lombok.Data;

@Data
public class UsuarioEstadoAsistenciasDTO {
    private int id;
    private String nombre;
    private int faltas;
    private int asistencias;
    private int licencias;
    private int atrasos;
    private int numGrupos;
    private int numProgramaciones;

    public UsuarioEstadoAsistenciasDTO(Integer id, String name, Integer faltas, Integer asistencias, Integer licencias, Integer atrasos, Integer numGrupos, Integer numProgramaciones) {
        this.id = id;
        this.nombre = name;
        this.faltas = faltas;
        this.asistencias = asistencias;
        this.licencias = licencias;
        this.atrasos = atrasos;
        this.numGrupos = numGrupos;
        this.numProgramaciones = numProgramaciones;
    }
}

