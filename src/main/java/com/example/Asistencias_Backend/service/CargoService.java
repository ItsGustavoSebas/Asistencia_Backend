package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.entity.Cargo;
import com.example.Asistencias_Backend.repository.CargoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepo cargoRepo;

    public List<Cargo> getCargos() {
        return cargoRepo.findAll();
    }

}

