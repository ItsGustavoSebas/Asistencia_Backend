package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Materia;
import com.example.Asistencias_Backend.repository.MateriaRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MateriaService {
    @Autowired
    private MateriaRepo materiaRepo;

    @Transactional
    public ReqRes updateMateria(int materia_id, Materia materia) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Materia> materiaOptional = materiaRepo.findById(materia_id);
            if (materiaOptional.isPresent()) {
                Materia existingMateria = materiaOptional.get();
                existingMateria.setSigla(materia.getSigla());
                existingMateria.setName(materia.getName());
                Materia saveMateria = materiaRepo.save(existingMateria);
                reqRes.setMateria(saveMateria);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Materia updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Materia not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating materia: " + e.getMessage());
        }
        return reqRes;
    }

    public Materia getMateria(int materiaId) {
        return materiaRepo.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Materia not found"));
    }

    public List<Materia> getMateria() {
        return materiaRepo.findAll();
    }

    public ReqRes createMateria(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            Materia materia = new Materia();
            materia.setName(registrationRequest.getName());
            materia.setSigla(registrationRequest.getSigla());
            Materia materiaResult = materiaRepo.save(materia);

            if (materiaResult.getId() > 0) {
                resp.setMateria(materiaResult);
                resp.setMessage("Materia Saved Successfully");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteMateria(int materiaId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Materia> materiaOptional = materiaRepo.findById(materiaId);
            if (materiaOptional.isPresent()) {
                materiaRepo.deleteById(materiaId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Materia deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Materia not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting materia: " + e.getMessage());
        }
        return reqRes;
    }
}
