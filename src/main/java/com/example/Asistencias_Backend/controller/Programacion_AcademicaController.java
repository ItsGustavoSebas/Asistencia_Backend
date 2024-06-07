package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.service.Programacion_AcademicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminuser/programacion")
public class Programacion_AcademicaController {
    @Autowired
    private Programacion_AcademicaService programacionAcademicaService;

    @GetMapping("")
    public ResponseEntity<ReqRes> getCarreras() {
        ReqRes programacion = programacionAcademicaService.getProgramacion();
        return ResponseEntity.ok(programacion);
    }
}