package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.*;
import com.example.Asistencias_Backend.entity.Carrera;
import com.example.Asistencias_Backend.service.AsistenciaService;
import com.example.Asistencias_Backend.service.Programacion_AcademicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    @PostMapping("/crear")
    public ResponseEntity<ReqRes> createProgramacion(@RequestBody newGrupoDTO grupoDto) {
        ReqRes programacion = programacionAcademicaService.createGrupo(grupoDto);
        return ResponseEntity.ok(programacion);
    }

    @GetMapping("/facultadGestion/{id}")
    public ResponseEntity<ReqRes> getFacultad(@PathVariable int id) {
        ReqRes programacion = programacionAcademicaService.getFacultad(id);
        return ResponseEntity.ok(programacion);
    }

    @GetMapping("/grupos")
    public ResponseEntity<List<GrupoDTO>> getGrupos() {
        List<GrupoDTO> grupos = programacionAcademicaService.getGrupos();
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/grupos/{facultadId}")
    public ResponseEntity<List<GrupoDTO>> getGruposFac(@PathVariable int facultadId) {
        List<GrupoDTO> grupos = programacionAcademicaService.getGruposFacultad(facultadId);
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/carreras/{facultadId}")
    public ResponseEntity<ReqRes> getCarrerasFac(@PathVariable int facultadId) {
        ReqRes carreras = programacionAcademicaService.getCarreras(facultadId);
        return ResponseEntity.ok(carreras);
    }

    @GetMapping("/modulos/{facultadId}")
    public ResponseEntity<ReqRes> getModulosFac(@PathVariable int facultadId) {
        ReqRes res = programacionAcademicaService.getModulos(facultadId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/carreras/materias/{carreraId}")
    public ResponseEntity<ReqRes> getMaterias_CarrerasFac(@PathVariable int carreraId) {
        ReqRes res = programacionAcademicaService.getCarrera_Materias(carreraId);
        return ResponseEntity.ok(res);
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
    public ResponseEntity<List<AsistenciaDTO>> getAsistencias() {
        List<AsistenciaDTO> asistencias = asistenciaService.getAsistencias();
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/{user_id}")
    public ResponseEntity<List<AsistenciaDTO>> getAsistencias(@PathVariable int user_id) {
        List<AsistenciaDTO> asistencias = asistenciaService.getAsistenciasByUserId(user_id);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/web/{user_id}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasA(@PathVariable int user_id) {
        List<AsistenciasDTO> asistencias = asistenciaService.getAsistenciasByUserIdWeb(user_id);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/{user_id}/{estado}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistencias(@PathVariable int user_id, @PathVariable String estado) {
        List<AsistenciasDTO> asistencias = asistenciaService.getAsistenciasByUserIdEstado(user_id, estado);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/users")
    public ResponseEntity<List<AsistenciaUsuarioDTO>> getAsistenciasUsers() {
        List<AsistenciaUsuarioDTO> asistencias = asistenciaService.getAllUsersAsistencias();
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/estados")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstados() {
        List<UsuarioEstadoAsistenciasDTO> asistencias = asistenciaService.getAllUsersEstadoAsistencias();
        return ResponseEntity.ok(asistencias);
    }
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @GetMapping("/asistencias/users/{startDate}/{endDate}")
    public ResponseEntity<List<AsistenciaUsuarioDTO>> getAsistenciasUsersDate(@PathVariable String startDate,
                                                                              @PathVariable String endDate) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<AsistenciaUsuarioDTO> asistencias = asistenciaService.getAllUsersAsistenciasDate(parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/estados/{startDate}/{endDate}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosDate(@PathVariable String startDate,
                                                                                       @PathVariable String endDate) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<UsuarioEstadoAsistenciasDTO> asistencias = asistenciaService.getAllUsersEstadoAsistenciasDate(parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/name/estados/{startDate}/{endDate}")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosNameDate(@PathVariable String startDate,
                                                                                       @PathVariable String endDate, @RequestParam String name) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<UsuarioEstadoAsistenciasDTO> asistencias = asistenciaService.getAllUsersEstadoAsistenciasNameDate(name, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/name/estados")
    public ResponseEntity<List<UsuarioEstadoAsistenciasDTO>> getAsistenciasEstadosNameDate(@RequestParam String name) {
        List<UsuarioEstadoAsistenciasDTO> asistencias = asistenciaService.getAllUsersEstadoAsistenciasName(name);
        return ResponseEntity.ok(asistencias);
    }

    @GetMapping("/asistencias/web/{user_id}/{startDate}/{endDate}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasDate(@PathVariable int user_id,
                                                                  @PathVariable String startDate,
                                                                  @PathVariable String endDate) throws ParseException {
        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);
        List<AsistenciasDTO> asistencias = asistenciaService.getAsistenciasByUserIdDate(user_id, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }



    @GetMapping("/asistencias/{user_id}/{estado}/{startDate}/{endDate}")
    public ResponseEntity<List<AsistenciasDTO>> getAsistenciasDate(
            @PathVariable int user_id,
            @PathVariable String estado,
            @PathVariable String startDate,
            @PathVariable String endDate) throws ParseException {

        Date parsedStartDate = dateFormat.parse(startDate);
        Date parsedEndDate = dateFormat.parse(endDate);

        List<AsistenciasDTO> asistencias = asistenciaService.getAsistenciasByUserIdEstadoDate(user_id, estado, parsedStartDate, parsedEndDate);
        return ResponseEntity.ok(asistencias);
    }

}