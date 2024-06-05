package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Modulo;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import com.example.Asistencias_Backend.repository.ModuloRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ModuloService {
    @Autowired
    private ModuloRepo moduloRepo;
    @Autowired
    private FacultadRepo facultadRepo;

    @Transactional
    public ReqRes updateModulo(int modulo_id, ReqRes modulo) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Modulo> moduloOptional = moduloRepo.findById(modulo_id);
            if (moduloOptional.isPresent()) {
                Modulo existingModulo = moduloOptional.get();
                existingModulo.setName(modulo.getName());
                existingModulo.setFacultad(facultadRepo.findById(modulo.getFacultad().getId()).orElse(null));
                existingModulo.setLatitud(modulo.getLatitud());
                existingModulo.setLongitud(modulo.getLongitud());
                Modulo savedModulo = moduloRepo.save(existingModulo);
                reqRes.setModulo(savedModulo);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Modulo updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Modulo not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating modulo: " + e.getMessage());
        }
        return reqRes;
    }





    public Modulo getModulo(int moduloId) {
        return moduloRepo.findById(moduloId)
                .orElseThrow(() -> new RuntimeException("Modulo not found"));
    }

    public ReqRes getModulo() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Modulo> result = moduloRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setModuloList(result);
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

    public ReqRes createModulo(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            Modulo modulo = new Modulo();
            modulo.setName(registrationRequest.getName());
            modulo.setFacultad(facultadRepo.findById(registrationRequest.getFacultad().getId()).orElse(null));
            modulo.setLatitud(registrationRequest.getLatitud());
            modulo.setLongitud(registrationRequest.getLongitud());
            Modulo moduloResult = moduloRepo.save(modulo);

            if (moduloResult.getId() > 0) {
                resp.setModulo(moduloResult);
                resp.setMessage("Modulo Saved Successfully");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteModulo(int moduloId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Modulo> moduloOptional = moduloRepo.findById(moduloId);
            if (moduloOptional.isPresent()) {
                moduloRepo.deleteById(moduloId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Modulo deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Modulo not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting modulo: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes searchModulosByName(String name) {
        ReqRes reqRes = new ReqRes();

        try {
            List<Modulo> result = moduloRepo.findByNameContainingIgnoreCase(name);
            if (!result.isEmpty()) {
                reqRes.setModuloList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No modulos found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }
}
