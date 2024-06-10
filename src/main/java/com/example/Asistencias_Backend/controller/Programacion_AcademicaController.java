package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.AsistenciaDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Asistencia;
import com.example.Asistencias_Backend.service.AsistenciaService;
import com.example.Asistencias_Backend.service.Programacion_AcademicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/adminuser/programacion")
public class Programacion_AcademicaController {
    @Autowired
    private Programacion_AcademicaService programacionAcademicaService;

    @Autowired
    private AsistenciaService asistenciaService;

    @GetMapping("")
    public ResponseEntity<ReqRes> getProgramacion() {
        ReqRes programacion = programacionAcademicaService.getProgramacion();
        return ResponseEntity.ok(programacion);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<ReqRes> getProgramacionUser(@PathVariable int user_id) {
        ReqRes programacion = programacionAcademicaService.getProgramacionesAcademicasByUsuario(user_id );
        return ResponseEntity.ok(programacion);
    }

    @PostMapping("/marcar")
    public ResponseEntity<ReqRes> marcarAsistencia(@RequestBody ReqRes solicitud) {
        LocalDateTime fecha = LocalDateTime.parse(solicitud.getFecha());
        return ResponseEntity.ok(programacionAcademicaService.marcarAsistencia(solicitud.getAsistenciaId(), fecha, solicitud.getLatitud(), solicitud.getLongitud()));
    }

    @PostMapping("/licencia")
    public ResponseEntity<ReqRes> solicitarLicencia(@RequestBody ReqRes solicitud) {
        LocalDate inicio = LocalDate.parse(solicitud.getFechaInicio());
        LocalDate fin = LocalDate.parse(solicitud.getFechaFin());
        return ResponseEntity.ok(programacionAcademicaService.solicitarLicencia(solicitud.getProgramacionAcademicaId(), inicio, fin, solicitud.getMotivo()));
    }

    @GetMapping("/asistencias")
    public ResponseEntity<ReqRes> getAsistencias() {
        ReqRes asistencias = programacionAcademicaService.getAsistencias();
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/{user_id}")
    public ResponseEntity<List<AsistenciaDTO>> getAsistencias(@PathVariable int user_id) {
        List<AsistenciaDTO> asistencias = asistenciaService.getAsistenciasByUserId(user_id);
        return ResponseEntity.ok(asistencias);
    }

}