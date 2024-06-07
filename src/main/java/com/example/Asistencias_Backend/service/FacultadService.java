package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.entity.Facultad;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultadService {
    private final FacultadRepo facultadRepo;

    public FacultadService(FacultadRepo facultadRepo) {
        this.facultadRepo = facultadRepo;
    }

    public List<Facultad> getAllFacultades() {
        return facultadRepo.findAll();
    }
}
