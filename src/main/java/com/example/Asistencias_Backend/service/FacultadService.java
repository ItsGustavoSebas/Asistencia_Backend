package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Facultad;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultadService {


    private final FacultadRepo facultadRepo;

    public FacultadService(FacultadRepo facultadRepo) {
        this.facultadRepo = facultadRepo;
    }

    public List<Facultad> getAllFacultades() {
        return facultadRepo.findAll();
    }

    @Transactional
    public ReqRes updateFacultad(int facultad_id, Facultad facultad) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Facultad> facultadOptional = facultadRepo.findById(facultad_id);
            if (facultadOptional.isPresent()) {
                Facultad existingFacultad = facultadOptional.get();
                existingFacultad.setName(facultad.getName());
                Facultad saveFacultad = facultadRepo.save(existingFacultad);
                reqRes.setFacultad(saveFacultad);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Facultad updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Facultad not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating facultad: " + e.getMessage());
        }
        return reqRes;
    }

    public Facultad getFacultad(int facultadId) {
        return facultadRepo.findById(facultadId)
                .orElseThrow(() -> new RuntimeException("Facultad not found"));
    }

    public ReqRes getFacultad() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Facultad> result = facultadRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setFacultadList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No facultades found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes createFacultad(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            Facultad facultad = new Facultad();
            facultad.setName(registrationRequest.getName());
            Facultad facultadResult = facultadRepo.save(facultad);

            if (facultadResult.getId() > 0) {
                resp.setFacultad(facultadResult);
                resp.setMessage("Facultad Saved Successfully");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteFacultad(int facultadId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Facultad> facultadOptional = facultadRepo.findById(facultadId);
            if (facultadOptional.isPresent()) {
                facultadRepo.deleteById(facultadId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Facultad deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Facultad not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting facultad: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes searchFacultadsByName(String name) {
        ReqRes reqRes = new ReqRes();

        try {
            List<Facultad> result = facultadRepo.findByNameContainingIgnoreCase(name);
            if (!result.isEmpty()) {
                reqRes.setFacultadList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No facultades found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }
}
