package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Facultad;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component("FacultadSeeder")
public class FacultadSeeder implements CommandLineRunner, Ordered {

    @Override
    public int getOrder() {
        return 1;
    }

    @Autowired
    private FacultadRepo facultadRepo;

    @Override
    public void run(String... args) throws Exception {
        Facultad facultad1 = new Facultad();
        facultad1.setName("Facultad de Ciencias en la Computación y Telecomunicaciones");
        facultadRepo.save(facultad1);

        Facultad facultad2 = new Facultad();
        facultad2.setName("Facultad de Ciencias Exactas y Tecnologia");
        facultadRepo.save(facultad2);

    }
}
