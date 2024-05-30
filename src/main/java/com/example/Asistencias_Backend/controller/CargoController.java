package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.entity.Cargo;
import com.example.Asistencias_Backend.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping("")
    public ResponseEntity<List<Cargo>> getCargos() {
        List<Cargo> cargos = cargoService.getCargos();
        return ResponseEntity.ok(cargos);
    }
}
