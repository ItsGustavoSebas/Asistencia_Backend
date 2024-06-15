package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.AsistenciasDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.dto.UsuarioEstadoAsistenciasDTO;
import com.example.Asistencias_Backend.entity.Carrera;
import com.example.Asistencias_Backend.entity.Materia_Carrera;
import com.example.Asistencias_Backend.service.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public ResponseEntity<Carrera> getCarrera(@PathVariable int carreraId) {
        Carrera carrera = carreraService.getCarrera(carreraId);
        return ResponseEntity.ok(carrera);
    }

    @GetMapping("/get-carreras-names")
    public ResponseEntity<ReqRes> getCarrerasByName(@RequestParam String name){
        ReqRes carreras = carreraService.searchCarrerasByName(name);
        return ResponseEntity.ok(carreras);

    }

    @PostMapping("/crear")
    public ResponseEntity<ReqRes> createCarrera(@RequestBody ReqRes reg){
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

    @GetMapping("/asistencias/estados/{carreraId}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstados(@PathVariable int carreraId) {
        List<UsuarioEstadoAsistenciasDTO> asistencias = carreraService.getAllUsersEstadoAsistencias(carreraId);
        return ResponseEntity.ok(asistencias);
    }

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/asistencias/estados/{carreraId}/{startDate}/{endDate}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosDate(@PathVariable int carreraId, @PathVariable String startDate,
                                                                                       @PathVariable String endDate) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<UsuarioEstadoAsistenciasDTO> asistencias = carreraService.getAllUsersEstadoAsistenciasDate(carreraId, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/name/{carreraId}/{startDate}/{endDate}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosNameDate(@PathVariable int carreraId, @PathVariable String startDate,
                                                                                           @PathVariable String endDate, @RequestParam String name) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<UsuarioEstadoAsistenciasDTO> asistencias = carreraService.getAllUsersEstadoAsistenciasNameDate(carreraId, name, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/name/{carreraId}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosNameDate(@PathVariable int carreraId, @RequestParam String name) {
        List<UsuarioEstadoAsistenciasDTO> asistencias = carreraService.getAllUsersEstadoAsistenciasName(carreraId, name);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/web/{carreraId}/{startDate}/{endDate}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasDate(@PathVariable int carreraId,
                                                                   @PathVariable String startDate,
                                                                   @PathVariable String endDate) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<AsistenciasDTO> asistencias = carreraService.getAsistenciasByUserIdDate(carreraId, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/{carreraId}/{estado}/{startDate}/{endDate}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasDate(
            @PathVariable int carreraId,
            @PathVariable String estado,
            @PathVariable String startDate,
            @PathVariable String endDate) throws ParseException {

        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);

        List<AsistenciasDTO> asistencias = carreraService.getAsistenciasByUserIdEstadoDate(carreraId, estado, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/web/{carreraId}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasA(@PathVariable int carreraId) {
        List<AsistenciasDTO> asistencias = carreraService.getAsistenciasByUserIdWeb(carreraId);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/{carreraId}/{estado}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistencias(@PathVariable int carreraId, @PathVariable String estado) {
        List<AsistenciasDTO> asistencias = carreraService.getAsistenciasByUserIdEstado(carreraId, estado);
        return ResponseEntity.ok(asistencias);
    }
}
