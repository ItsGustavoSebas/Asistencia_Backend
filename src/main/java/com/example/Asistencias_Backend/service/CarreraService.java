
package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Carrera;
import com.example.Asistencias_Backend.entity.Materia;
import com.example.Asistencias_Backend.entity.Materia_Carrera;
import com.example.Asistencias_Backend.repository.CarreraRepo;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import com.example.Asistencias_Backend.repository.MateriaRepo;
import com.example.Asistencias_Backend.repository.Materia_CarreraRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
                List<Materia_Carrera> existingMateriaCarreras = materia_CarreraRepo.findByCarrera(existingCarrera);
                Map<Integer, Materia_Carrera> existingMateriaMap = existingMateriaCarreras.stream()
                        .collect(Collectors.toMap(mc -> mc.getMateria().getId(), mc -> mc));
                List<Materia_Carrera> newMateriaCarreras = new ArrayList<>();
                for (ReqRes.MateriaSemestre materiaSemestre : reqRes.getMaterias()) {
                    int materiaId = materiaSemestre.getMateriaId();
                    int semestre = materiaSemestre.getSemestre();
                    if (!existingMateriaMap.containsKey(materiaId)) {
                        Materia materia = materiaRepo.findById(materiaId)
                                .orElseThrow(() -> new RuntimeException("Materia not found"));
                        Materia_Carrera newMateriaCarrera = new Materia_Carrera();
                        newMateriaCarrera.setSemestre(semestre);
                        newMateriaCarrera.setMateria(materia);
                        newMateriaCarrera.setCarrera(existingCarrera);

                        newMateriaCarreras.add(newMateriaCarrera);
                    }
                }
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

    public List<Carrera> getCarrera() {
        return carreraRepo.findAll();
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
}
