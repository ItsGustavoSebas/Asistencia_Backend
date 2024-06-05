package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Modulo;
import com.example.Asistencias_Backend.service.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminuser/modulos")
public class ModuloController {
    @Autowired
    private ModuloService moduloService;

    @GetMapping("")
    public ResponseEntity<ReqRes> getModulos() {
        ReqRes modulos = moduloService.getModulo();
        return ResponseEntity.ok(modulos);
    }

    @GetMapping("/{moduloId}")
    public ResponseEntity<Modulo> getRole(@PathVariable int moduloId) {
        Modulo modulo = moduloService.getModulo(moduloId);
        return ResponseEntity.ok(modulo);
    }

    @PostMapping("/crear")
    public ResponseEntity<ReqRes> crear(@RequestBody ReqRes reg){
        return ResponseEntity.ok(moduloService.createModulo(reg));
    }

    @PostMapping("/update/{moduloId}")
    public ResponseEntity<ReqRes> actualizar(@PathVariable int moduloId, @RequestBody ReqRes reg){
        return ResponseEntity.ok(moduloService.updateModulo(moduloId, reg));
    }

    @DeleteMapping("/delete/{moduloId}")
    public ResponseEntity<ReqRes> deleteModulo(@PathVariable int moduloId){
        return ResponseEntity.ok(moduloService.deleteModulo(moduloId));
    }

    @GetMapping("/get-modulos-names")
    public ResponseEntity<ReqRes> getUsersByName(@RequestParam String name){
        ReqRes modulos = moduloService.searchModulosByName(name);
        return ResponseEntity.ok(modulos);

    }

}