package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.LicenciaDTO;
import com.example.Asistencias_Backend.service.LicenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminuser/licencias")
public class LicenciaUserController {

    @Autowired
    private LicenciaService licenciaService;

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<LicenciaDTO>> getLicenciasUser(@PathVariable int user_id) {
        List<LicenciaDTO> licencias = licenciaService.getLicencia(user_id);
        return ResponseEntity.ok(licencias);
    }
}
