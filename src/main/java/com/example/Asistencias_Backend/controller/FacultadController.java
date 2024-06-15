package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.AsistenciasDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.dto.UsuarioEstadoAsistenciasDTO;
import com.example.Asistencias_Backend.entity.Facultad;
import com.example.Asistencias_Backend.service.AsistenciaService;
import com.example.Asistencias_Backend.service.FacultadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/adminuser/facultades")
public class FacultadController {
    @Autowired
    private FacultadService facultadService;

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

    @GetMapping("/asistencias/estados/{facultadId}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstados(@PathVariable int facultadId) {
        List<UsuarioEstadoAsistenciasDTO> asistencias = facultadService.getAllUsersEstadoAsistencias(facultadId);
        return ResponseEntity.ok(asistencias);
    }

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/asistencias/estados/{facultadId}/{startDate}/{endDate}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosDate(@PathVariable int facultadId, @PathVariable String startDate,
                                                                                       @PathVariable String endDate) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<UsuarioEstadoAsistenciasDTO> asistencias = facultadService.getAllUsersEstadoAsistenciasDate(facultadId, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/name/{facultadId}/{startDate}/{endDate}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosNameDate(@PathVariable int facultadId, @PathVariable String startDate,
                                                                                           @PathVariable String endDate, @RequestParam String name) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<UsuarioEstadoAsistenciasDTO> asistencias = facultadService.getAllUsersEstadoAsistenciasNameDate(facultadId, name, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/name/{facultadId}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosNameDate(@PathVariable int facultadId, @RequestParam String name) {
        List<UsuarioEstadoAsistenciasDTO> asistencias = facultadService.getAllUsersEstadoAsistenciasName(facultadId, name);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/web/{facultadId}/{startDate}/{endDate}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasDate(@PathVariable int facultadId,
                                                                   @PathVariable String startDate,
                                                                   @PathVariable String endDate) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<AsistenciasDTO> asistencias = facultadService.getAsistenciasByUserIdDate(facultadId, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/{facultadId}/{estado}/{startDate}/{endDate}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasDate(
            @PathVariable int facultadId,
            @PathVariable String estado,
            @PathVariable String startDate,
            @PathVariable String endDate) throws ParseException {

        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);

        List<AsistenciasDTO> asistencias = facultadService.getAsistenciasByUserIdEstadoDate(facultadId, estado, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/web/{facultadId}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasA(@PathVariable int facultadId) {
        List<AsistenciasDTO> asistencias = facultadService.getAsistenciasByUserIdWeb(facultadId);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/{facultadId}/{estado}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistencias(@PathVariable int facultadId, @PathVariable String estado) {
        List<AsistenciasDTO> asistencias = facultadService.getAsistenciasByUserIdEstado(facultadId, estado);
        return ResponseEntity.ok(asistencias);
    }
}

