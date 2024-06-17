package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.FechaDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.service.CalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminuser/calendario")
public class CalendarioController {
    @Autowired
    private CalendarioService calendarioService;

    @GetMapping("/gestiones/{facultadId}")
    public ResponseEntity<ReqRes> getGestiones(@PathVariable int facultadId){
        return ResponseEntity.ok(calendarioService.getGestiones(facultadId));
    }

    @GetMapping("/tipos")
    public ResponseEntity<ReqRes> getTipos(){
        return ResponseEntity.ok(calendarioService.getTipos());
    }

    @GetMapping("/fechas/{gestionId}")
    public ResponseEntity<ReqRes> getFechas(@PathVariable int gestionId){
        return ResponseEntity.ok(calendarioService.getFechas(gestionId));
    }

    @PostMapping("/crear")
    public ResponseEntity<ReqRes> crear(@RequestBody FechaDTO reg){
        return ResponseEntity.ok(calendarioService.createFecha(reg));
    }

    @PostMapping("/update/{fechaId}")
    public ResponseEntity<ReqRes> actualizar(@PathVariable int fechaId, @RequestBody FechaDTO reg){
        return ResponseEntity.ok(calendarioService.editarFecha(reg, fechaId));
    }

    @DeleteMapping("/delete/{fechaId}")
    public ResponseEntity<ReqRes> deleteMateria(@PathVariable int fechaId){
        return ResponseEntity.ok(calendarioService.deleteFecha(fechaId));
    }
}
