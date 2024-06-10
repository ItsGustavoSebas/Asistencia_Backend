package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Programacion_AcademicaService {

    @Autowired
    private Programacion_AcademicaRepo programacion_AcademicaRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private GrupoRepo grupoRepo;
    @Autowired
    private AsistenciaRepo asistenciaRepo;
    @Autowired
    private LicenciaRepo licenciaRepo;

    public ReqRes getProgramacion() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Grupo> result = grupoRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setGrupoList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No grupos found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes getProgramacionesAcademicasByUsuario(int usuarioId) {
        ReqRes reqRes = new ReqRes();
        try {
            OurUsers usuario = usersRepo.findById(usuarioId).orElse(null);
            if (usuario == null) {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            List<Grupo> grupos = usuario.getGrupos();
            List<Programacion_Academica> programacionesAcademicas = grupos.stream()
                    .flatMap(grupo -> grupo.getProgramacionAcademicas().stream())
                    .collect(Collectors.toList());
            if (!programacionesAcademicas.isEmpty()) {
                reqRes.setProgramacionAcademicas(programacionesAcademicas);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No programaciones found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }

    }

    public ReqRes marcarAsistencia(int asistenciaId, LocalDateTime fecha, double latitud, double longitud) {
        ReqRes reqRes = new ReqRes();
        try {
            Asistencia asistencia = asistenciaRepo.getById(asistenciaId);
            LocalTime horaInicio = asistencia.getProgramacionAcademica().getDiaHorario().getHorario().getHoraInicio();
            LocalDateTime horaInicioLimite = fecha.withHour(horaInicio.getHour()).withMinute(horaInicio.getMinute()).plusMinutes(30);
            boolean puntual = fecha.isBefore(horaInicioLimite);

            Modulo modulo = asistencia.getProgramacionAcademica().getModulo();
            double distancia = calcularDistancia(latitud, longitud, modulo.getLatitud(), modulo.getLongitud());
            boolean dentroDelLimite = distancia <= 80.0;

            if (dentroDelLimite) {
                asistencia.setFecha(fecha);
                asistencia.setLatitud(latitud);
                asistencia.setLongitud(longitud);
                if(puntual) {
                    asistencia.setEstado("Presente");
                }else{
                    asistencia.setEstado("Atraso");
                }
                reqRes.setAsistencia(asistenciaRepo.save(asistencia));
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Fuera del limite de distancia (80 metros)");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    private static final int RADIO_TIERRA_METROS = 6371000;

    public double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
        double lat1Rad = Math.toRadians(latitud1);
        double lon1Rad = Math.toRadians(longitud1);
        double lat2Rad = Math.toRadians(latitud2);
        double lon2Rad = Math.toRadians(longitud2);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distanciaMetros = RADIO_TIERRA_METROS * c;

        return distanciaMetros;
    }

    public ReqRes solicitarLicencia(int programacionAcademicaId, LocalDate fechaInicio, LocalDate fechaFin, String motivo) {
        ReqRes resp = new ReqRes();
        try{
        Programacion_Academica programacionAcademica = programacion_AcademicaRepo.findById(programacionAcademicaId)
                .orElseThrow(() -> new RuntimeException("Programacion Academica no encontrada"));
        Licencia licencia = new Licencia();
        licencia.setFechaInicio(fechaInicio);
        licencia.setFechaFin(fechaFin);
        licencia.setMotivo(motivo);
        licencia.setProgramacionAcademica(programacionAcademica);
        long daysBetween = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        if (daysBetween <= 3) {
            licencia.setAprobado(true);
        } else {
            licencia.setAprobado(null);
        }
        Licencia licenciaResult = licenciaRepo.save(licencia);
        if (licenciaResult.getId() > 0) {
            resp.setLicencia(licenciaResult);
            resp.setMessage("Modulo Saved Successfully");
            resp.setStatusCode(200);
        }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
            return resp;
    }

    public ReqRes getAsistencias() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Asistencia> result = asistenciaRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setAsistenciaList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No grupos found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


}

