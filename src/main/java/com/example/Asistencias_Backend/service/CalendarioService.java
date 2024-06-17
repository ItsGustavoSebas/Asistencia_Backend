package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.FechaDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CalendarioService {

    @Autowired
    private FacultadRepo facultadRepo;

    @Autowired
    private GestionRepo gestionRepo;

    @Autowired
    private TipoRepo tipoRepo;

    @Autowired
    private Fecha_ImportanteRepo fechaImportanteRepo;

    @Autowired
    private Facultad_GestionRepo facultadGestionRepo;

    public ReqRes getGestiones(int facultadId) {
        ReqRes reqRes = new ReqRes();
        try {
            Facultad facultad = facultadRepo.findById(facultadId).orElseThrow(() -> new RuntimeException("User Not found"));
            List<Facultad_Gestion> result = facultad.getFacultadGestions();
            if (!result.isEmpty()) {
                reqRes.setFacultadGestions(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No gestiones found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes getTipos() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Tipo> result = tipoRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setTipos(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No tipos found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes createFecha(FechaDTO registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            Facultad_Gestion facultadGestion = facultadGestionRepo.findById(registrationRequest.getGestionId()).orElseThrow(() -> new RuntimeException("Facultad Not found"));
            Tipo tipo = tipoRepo.findById(registrationRequest.getTipoId()).orElseThrow(() -> new RuntimeException("Tipo Not found"));
            FechaImportante fechaImportante1 = new FechaImportante();
            fechaImportante1.setFechaInicio(registrationRequest.getFechaInicio());
            fechaImportante1.setFechaFin(registrationRequest.getFechaFin());
            fechaImportante1.setDescripcion(registrationRequest.getDescripcion());
            fechaImportante1.setFacultadGestion(facultadGestion);
            fechaImportante1.setTipo(tipo);
            FechaImportante fechaImportante = fechaImportanteRepo.save(fechaImportante1);
            if (fechaImportante.getId() > 0) {
                resp.setMessage("Fecha Saved Successfully");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes editarFecha(FechaDTO registrationRequest, int fechaId){
        ReqRes resp = new ReqRes();

        try {
            FechaImportante fechaImportante1 = fechaImportanteRepo.findById(fechaId).orElseThrow(() -> new RuntimeException("Fecha Not found"));
            Facultad_Gestion facultadGestion = facultadGestionRepo.findById(registrationRequest.getGestionId()).orElseThrow(() -> new RuntimeException("Facultad Not found"));;
            Tipo tipo = tipoRepo.findById(registrationRequest.getTipoId()).orElseThrow(() -> new RuntimeException("Tipo Not found"));;
            fechaImportante1.setFechaInicio(registrationRequest.getFechaInicio());
            fechaImportante1.setFechaFin(registrationRequest.getFechaFin());
            fechaImportante1.setDescripcion(registrationRequest.getDescripcion());
            fechaImportante1.setFacultadGestion(facultadGestion);
            fechaImportante1.setTipo(tipo);
            FechaImportante fechaImportante = fechaImportanteRepo.save(fechaImportante1);
            if (fechaImportante.getId() > 0) {
                resp.setMessage("Fecha Saved Successfully");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteFecha(int fechaId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<FechaImportante> fechaOptional = fechaImportanteRepo.findById(fechaId);
            if (fechaOptional.isPresent()) {
                facultadRepo.deleteById(fechaId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Fecha deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Fecha not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting fecha: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes getFechas(int gestionId) {
        ReqRes reqRes = new ReqRes();
        try {
            Facultad_Gestion gestion = facultadGestionRepo.findById(gestionId).orElseThrow(() -> new RuntimeException("User Not found"));;
            List<FechaImportante> result = gestion.getFechaImportantes();
            if (!result.isEmpty()) {
                reqRes.setFechaImportantes(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No fechas found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }
}
