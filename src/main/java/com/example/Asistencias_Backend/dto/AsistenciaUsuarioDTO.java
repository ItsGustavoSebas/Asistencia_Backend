package com.example.Asistencias_Backend.dto;

import com.example.Asistencias_Backend.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class AsistenciaUsuarioDTO {
    private String nombre;
    private int id;
    private List<AsistenciaDTO> asistencias;

    public AsistenciaUsuarioDTO(String name, Integer id, List<AsistenciaDTO> asistenciaDTO) {
        this.id = id;
        this.nombre = name;
        this.asistencias = asistenciaDTO;
    }
}
