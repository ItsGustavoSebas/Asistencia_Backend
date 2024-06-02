package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.entity.Facultad;
import com.example.Asistencias_Backend.entity.Modulo;
import com.example.Asistencias_Backend.service.FacultadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adminuser/facultades")
public class FacultadController {
    private final FacultadService facultadService;

    public FacultadController(FacultadService facultadService) {
        this.facultadService = facultadService;
    }

    @GetMapping("")
    public ResponseEntity<List<Facultad>> getModulos() {
        List<Facultad> facultades = facultadService.getAllFacultades();
        return ResponseEntity.ok(facultades);
    }
}
