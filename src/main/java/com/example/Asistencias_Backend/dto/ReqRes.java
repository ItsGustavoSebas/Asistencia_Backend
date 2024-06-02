package com.example.Asistencias_Backend.dto;

import com.example.Asistencias_Backend.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String city;
    private List<String> roles;
    private List<String> permissions;
    private String email;
    private String password;
    private OurUsers ourUsers;
    private List<OurUsers> ourUsersList;
    private List<Role> roleList;
    private Integer cargoId;
    private Role role;
    private Materia materia;
    private List<Materia> materiaList;
    private Facultad facultad;
    private List<Facultad> facultadList;
    private Carrera carrera;
    private List<Carrera> carreraList;
    private List<MateriaSemestre> materias;
    private Modulo modulo;
    private Double latitud;
    private Double longitud;
    private List<String> aulaNames;
    private String sigla;
    public static class MateriaSemestre {
        private int materiaId;
        private int semestre;

        // Getters y setters
        public int getMateriaId() {
            return materiaId;
        }

        public int getSemestre() {
            return semestre;
        }
    }

}
