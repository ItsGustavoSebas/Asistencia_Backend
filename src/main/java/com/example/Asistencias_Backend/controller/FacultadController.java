package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Facultad;
import com.example.Asistencias_Backend.service.FacultadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/f")
    public ResponseEntity<ReqRes> getFacultades() {
        ReqRes facultades = facultadService.getFacultad();
        return ResponseEntity.ok(facultades);
    }

    @GetMapping("/{facultadId}")
    public ResponseEntity<Facultad> getRole(@PathVariable int facultadId) {
        Facultad facultad = facultadService.getFacultad(facultadId);
        return ResponseEntity.ok(facultad);
    }

    @PostMapping("/crear")
    public ResponseEntity<ReqRes> crear(@RequestBody ReqRes reg){
        return ResponseEntity.ok(facultadService.createFacultad(reg));
    }

    @PostMapping("/update/{facultadId}")
    public ResponseEntity<ReqRes> actualizar(@PathVariable int facultadId, @RequestBody Facultad reg){
        return ResponseEntity.ok(facultadService.updateFacultad(facultadId, reg));
    }

    @DeleteMapping("/delete/{facultadId}")
    public ResponseEntity<ReqRes> deleteMateria(@PathVariable int facultadId){
        return ResponseEntity.ok(facultadService.deleteFacultad(facultadId));
    }

    @GetMapping("/get-facultades-names")
    public ResponseEntity<ReqRes> getFacultadsByName(@RequestParam String name){
        ReqRes facultades = facultadService.searchFacultadsByName(name);
        return ResponseEntity.ok(facultades);
    }

}

