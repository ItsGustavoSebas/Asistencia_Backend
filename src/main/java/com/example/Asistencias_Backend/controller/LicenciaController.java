package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.LicenciaDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.service.LicenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@RestController
@RequestMapping("/admin/licencias")
public class LicenciaController {
    @Autowired
    private LicenciaService licenciaService;
    @GetMapping("")
    public ResponseEntity<List<LicenciaDTO>> getLicencias() {
        List<LicenciaDTO> licencias = licenciaService.getLicencias();
        return ResponseEntity.ok(licencias);
    }

    @PostMapping("/aprobar/{licenciaId}")
    public ResponseEntity<ReqRes> aprobar(@PathVariable int licenciaId){
        return ResponseEntity.ok(licenciaService.aprobar(licenciaId));
    }

    @PostMapping("/rechazar/{licenciaId}")
    public ResponseEntity<ReqRes> rechazar(@PathVariable int licenciaId){
        return ResponseEntity.ok(licenciaService.rechazar(licenciaId));
    }

    @GetMapping("/{estado}")
    public ResponseEntity<List<LicenciaDTO>> getLicencias(@PathVariable String estado) {
        List<LicenciaDTO> licencias = licenciaService.getLicenciasByEstado(estado);
        return ResponseEntity.ok(licencias);
    }
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @GetMapping("/{startDate}/{endDate}")
    public ResponseEntity<List<LicenciaDTO>> getLicenciasDate(@PathVariable String startDate,
                                                              @PathVariable String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate, dateFormatter);
        LocalDate parsedEndDate = LocalDate.parse(endDate, dateFormatter);
        List<LicenciaDTO> licencias = licenciaService.getLicenciasDate(parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(licencias);
    }

    @GetMapping("/{estado}/{startDate}/{endDate}")
    public ResponseEntity<List<LicenciaDTO>> getLicenciasDate(@PathVariable String estado, @PathVariable String startDate,
                                                              @PathVariable String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate, dateFormatter);
        LocalDate parsedEndDate = LocalDate.parse(endDate, dateFormatter);
        List<LicenciaDTO> licencias = licenciaService.getLicenciasByEstadoDate(estado, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(licencias);
    }

}
