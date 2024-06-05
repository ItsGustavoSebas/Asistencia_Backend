package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Carrera;
import com.example.Asistencias_Backend.entity.Materia;
import com.example.Asistencias_Backend.entity.Materia_Carrera;
import com.example.Asistencias_Backend.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminuser/carreras")
public class CarreraController {
    @Autowired
    private CarreraService carreraService;

    @GetMapping("")
    public ResponseEntity<ReqRes> getCarreras() {
        ReqRes carreras = carreraService.getCarrera();
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/{carreraId}")
    public ResponseEntity<Carrera> getRole(@PathVariable int carreraId) {
        Carrera carrera = carreraService.getCarrera(carreraId);
        return ResponseEntity.ok(carrera);
    }

    @GetMapping("/get-carreras-names")
    public ResponseEntity<ReqRes> getCarrerasByName(@RequestParam String name){
        ReqRes carreras = carreraService.searchCarrerasByName(name);
        return ResponseEntity.ok(carreras);

    }

    @PostMapping("/crear")
    public ResponseEntity<ReqRes> crear(@RequestBody ReqRes reg){
        return ResponseEntity.ok(carreraService.createCarrera(reg));
    }

    @PostMapping("/update/{carreraId}")
    public ResponseEntity<ReqRes> actualizar(@PathVariable int carreraId, @RequestBody ReqRes reg){
        return ResponseEntity.ok(carreraService.updateCarrera(carreraId, reg));
    }

    @DeleteMapping("/delete/{carreraId}")
    public ResponseEntity<ReqRes> deleteCarrera(@PathVariable int carreraId){
        return ResponseEntity.ok(carreraService.deleteCarrera(carreraId));
    }

    @GetMapping("/materias/{carreraId}")
    public ResponseEntity<List<Materia_Carrera>> getMaterias(@PathVariable int carreraId) {
        List<Materia_Carrera> materias = carreraService.getMaterias(carreraId);
        return ResponseEntity.ok(materias);
    }
}
