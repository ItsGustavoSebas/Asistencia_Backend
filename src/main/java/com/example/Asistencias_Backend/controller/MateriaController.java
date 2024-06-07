package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Materia;
import com.example.Asistencias_Backend.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminuser/materias")
public class MateriaController {
    @Autowired
    private MateriaService materiaService;

    @GetMapping("")
    public ResponseEntity<ReqRes> getMaterias() {
        ReqRes materias = materiaService.getMateria();
        return ResponseEntity.ok(materias);
    }

    @GetMapping("/{materiaId}")
    public ResponseEntity<Materia> getRole(@PathVariable int materiaId) {
        Materia materia = materiaService.getMateria(materiaId);
        return ResponseEntity.ok(materia);
    }

    @PostMapping("/crear")
    public ResponseEntity<ReqRes> crear(@RequestBody ReqRes reg){
        return ResponseEntity.ok(materiaService.createMateria(reg));
    }

    @PostMapping("/update/{materiaId}")
    public ResponseEntity<ReqRes> actualizar(@PathVariable int materiaId, @RequestBody Materia reg){
        return ResponseEntity.ok(materiaService.updateMateria(materiaId, reg));
    }

    @DeleteMapping("/delete/{materiaId}")
    public ResponseEntity<ReqRes> deleteMateria(@PathVariable int materiaId){
        return ResponseEntity.ok(materiaService.deleteMateria(materiaId));
    }

    @GetMapping("/get-materias-names")
    public ResponseEntity<ReqRes> getMateriasByName(@RequestParam String name){
        ReqRes materias = materiaService.searchMateriasByName(name);
        return ResponseEntity.ok(materias);
    }
}
