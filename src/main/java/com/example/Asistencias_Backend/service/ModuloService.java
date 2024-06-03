package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Aula;
import com.example.Asistencias_Backend.entity.Modulo;
import com.example.Asistencias_Backend.repository.AulaRepo;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import com.example.Asistencias_Backend.repository.ModuloRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ModuloService {
    @Autowired
    private ModuloRepo moduloRepo;
    @Autowired
    private AulaRepo aulaRepo;
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

                List<String> newAulaNames = modulo.getAulaNames();
                List<Aula> currentAulas = existingModulo.getAulas();

                // Remove aulas that are not in the new list of aula names
                currentAulas.removeIf(aula -> !newAulaNames.contains(aula.getName()));

                // Add new aulas from the new list of aula names
                for (String aulaName : newAulaNames) {
                    boolean aulaAlreadyExists = currentAulas.stream().anyMatch(aula -> aula.getName().equals(aulaName));
                    if (!aulaAlreadyExists) {
                        Aula aula = aulaRepo.findByName(aulaName);
                        if (aula == null) {
                            aula = new Aula();
                            aula.setName(aulaName);
                            aula.setModulo(existingModulo);
                            aula = aulaRepo.save(aula);
                        }
                        currentAulas.add(aula);
                    }
                }

                existingModulo.setAulas(currentAulas);
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

    public List<Modulo> getModulo() {
        return moduloRepo.findAll();
    }

    public ReqRes createModulo(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            Modulo modulo = new Modulo();
            modulo.setName(registrationRequest.getName());
            modulo.setFacultad(facultadRepo.findById(registrationRequest.getFacultad().getId()).orElse(null));
            modulo.setLatitud(registrationRequest.getLatitud());
            modulo.setLongitud(registrationRequest.getLongitud());
            moduloRepo.save(modulo);
            List<Aula> aulas = new ArrayList<>();
            for (String aulaName : registrationRequest.getAulaNames()) {
                Aula aula = new Aula();
                aula.setName(aulaName);
                aula.setModulo(modulo);
                aulas.add(aulaRepo.save(aula)); // Guardar cada aula y añadirla a la lista
            }
            modulo.setAulas(aulas);
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
}
