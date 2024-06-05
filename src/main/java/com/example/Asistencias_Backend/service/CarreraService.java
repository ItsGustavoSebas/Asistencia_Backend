
package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Carrera;
import com.example.Asistencias_Backend.entity.Materia;
import com.example.Asistencias_Backend.entity.Materia_Carrera;
import com.example.Asistencias_Backend.entity.OurUsers;
import com.example.Asistencias_Backend.repository.CarreraRepo;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import com.example.Asistencias_Backend.repository.MateriaRepo;
import com.example.Asistencias_Backend.repository.Materia_CarreraRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarreraService {
    @Autowired
    private CarreraRepo carreraRepo;
    @Autowired
    private MateriaRepo materiaRepo;
    @Autowired
    private FacultadRepo facultadRepo;
    @Autowired
    private Materia_CarreraRepo materia_CarreraRepo;

    @Transactional
    public ReqRes updateCarrera(int carrera_id, ReqRes reqRes) {
        ReqRes resp = new ReqRes();
        try {
            Optional<Carrera> carreraOptional = carreraRepo.findById(carrera_id);
            if (carreraOptional.isPresent()) {
                Carrera existingCarrera = carreraOptional.get();
                existingCarrera.setName(reqRes.getName());
                existingCarrera.setFacultad(facultadRepo.findById(reqRes.getFacultad().getId()).orElse(null));

                // Remove existing Materia_Carrera associations not in the new list
                List<Materia_Carrera> existingMateriaCarreras = materia_CarreraRepo.findByCarrera(existingCarrera);
                Set<Integer> newMateriaIds = reqRes.getMaterias().stream()
                        .map(ReqRes.MateriaSemestre::getMateriaId)
                        .collect(Collectors.toSet());

                for (Materia_Carrera existingMateriaCarrera : existingMateriaCarreras) {
                    if (!newMateriaIds.contains(existingMateriaCarrera.getMateria().getId())) {
                        materia_CarreraRepo.delete(existingMateriaCarrera);
                    }
                }

                // Create new Materia_Carrera associations
                List<Materia_Carrera> newMateriaCarreras = new ArrayList<>();
                for (ReqRes.MateriaSemestre materiaSemestre : reqRes.getMaterias()) {
                    int materiaId = materiaSemestre.getMateriaId();
                    int semestre = materiaSemestre.getSemestre();

                    Materia materia = materiaRepo.findById(materiaId)
                            .orElseThrow(() -> new RuntimeException("Materia not found"));

                    // Check if this Materia_Carrera association already exists
                    Optional<Materia_Carrera> existingMateriaCarreraOpt = existingMateriaCarreras.stream()
                            .filter(mc -> mc.getMateria().getId() == materiaId)
                            .findFirst();

                    if (existingMateriaCarreraOpt.isPresent()) {
                        // Update existing association with new semestre
                        Materia_Carrera existingMateriaCarrera = existingMateriaCarreraOpt.get();
                        existingMateriaCarrera.setSemestre(semestre);
                        newMateriaCarreras.add(existingMateriaCarrera);
                    } else {
                        // Create new association
                        Materia_Carrera newMateriaCarrera = new Materia_Carrera();
                        newMateriaCarrera.setSemestre(semestre);
                        newMateriaCarrera.setMateria(materia);
                        newMateriaCarrera.setCarrera(existingCarrera);
                        newMateriaCarreras.add(newMateriaCarrera);
                    }
                }

                // Save all new associations
                materia_CarreraRepo.saveAll(newMateriaCarreras);

                Carrera saveCarrera = carreraRepo.save(existingCarrera);
                resp.setCarrera(saveCarrera);
                resp.setStatusCode(200);
                resp.setMessage("Carrera updated successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Carrera not found for update");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred while updating carrera: " + e.getMessage());
        }
        return resp;
    }

    public Carrera getCarrera(int carreraId) {
        return carreraRepo.findById(carreraId)
                .orElseThrow(() -> new RuntimeException("Carrera not found"));
    }

    public ReqRes getCarrera() {
        ReqRes reqRes = new ReqRes();

        try {
            List<Carrera> result = carreraRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setCarreraList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No carreras found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes createCarrera(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            Carrera carrera = new Carrera();
            carrera.setName(registrationRequest.getName());
            carrera.setFacultad(facultadRepo.findById(registrationRequest.getFacultad().getId()).orElse(null));
            Carrera carreraResult = carreraRepo.save(carrera);
            if (carreraResult.getId() > 0) {
                List<ReqRes.MateriaSemestre> materiasSemestres = registrationRequest.getMaterias();
                List<Materia_Carrera> materiaCarreras = new ArrayList<>();
                for (ReqRes.MateriaSemestre materiaSemestre : materiasSemestres) {
                    Materia materia = materiaRepo.findById(materiaSemestre.getMateriaId()).orElseThrow(() -> new RuntimeException("Materia not found"));
                    Materia_Carrera materiaCarrera = new Materia_Carrera();
                    materiaCarrera.setSemestre(materiaSemestre.getSemestre());
                    materiaCarrera.setMateria(materia);
                    materiaCarrera.setCarrera(carreraResult);
                    materiaCarreras.add(materiaCarrera);
                }
                materia_CarreraRepo.saveAll(materiaCarreras);
                resp.setCarrera(carreraResult);
                resp.setMessage("Carrera Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteCarrera(int carreraId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Carrera> carreraOptional = carreraRepo.findById(carreraId);
            if (carreraOptional.isPresent()) {
                carreraRepo.deleteById(carreraId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Carrera deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Carrera not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting carrera: " + e.getMessage());
        }
        return reqRes;
    }

    public List<Materia_Carrera> getMaterias(int carreraId) {
        Carrera carrera = carreraRepo.findById(carreraId)
                .orElseThrow(() -> new RuntimeException("Carrera not found"));
        return materia_CarreraRepo.findByCarrera(carrera);
    }

    public ReqRes searchCarrerasByName(String name) {
        ReqRes reqRes = new ReqRes();
        try {
            List<Carrera> result = carreraRepo.findByNameContainingIgnoreCase(name);
            if (!result.isEmpty()) {
                reqRes.setCarreraList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No carreras found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }
}
