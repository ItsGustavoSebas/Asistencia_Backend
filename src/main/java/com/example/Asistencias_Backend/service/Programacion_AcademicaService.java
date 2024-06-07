package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Programacion_Academica;
import com.example.Asistencias_Backend.repository.Programacion_AcademicaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Programacion_AcademicaService {

    @Autowired
    private Programacion_AcademicaRepo programacion_AcademicaRepo;

    public Programacion_AcademicaService(Programacion_AcademicaRepo programacion_AcademicaRepo) {
        this.programacion_AcademicaRepo = programacion_AcademicaRepo;
    }

    public ReqRes getProgramacion() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Programacion_Academica> result = programacion_AcademicaRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setProgramacionAcademicas(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No materias found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }
}
