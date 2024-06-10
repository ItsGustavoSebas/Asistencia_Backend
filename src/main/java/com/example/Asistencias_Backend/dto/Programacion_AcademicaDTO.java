package com.example.Asistencias_Backend.dto;

import com.example.Asistencias_Backend.entity.Dia_Horario;
import com.example.Asistencias_Backend.entity.Grupo;
import com.example.Asistencias_Backend.entity.Modulo;
import com.example.Asistencias_Backend.entity.Programacion_Academica;
import lombok.Data;

@Data
public class Programacion_AcademicaDTO {
    private int id;
    private Integer aula;
    private Modulo modulo;
    private Grupo grupo;
    private Dia_Horario diaHorario;

    public Programacion_AcademicaDTO(Programacion_Academica programacionAcademica) {
        this.id = programacionAcademica.getId();
        this.aula = programacionAcademica.getAula();
        this.modulo = programacionAcademica.getModulo();
        this.grupo = programacionAcademica.getGrupo();
        this.diaHorario = programacionAcademica.getDiaHorario();
    }
}

