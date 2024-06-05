package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Modulo;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import com.example.Asistencias_Backend.repository.ModuloRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("ModuloSeeder")
@DependsOn({"FacultadSeeder"})
public class ModuloSeeder implements CommandLineRunner, Ordered {

    @Override
    public int getOrder() {
        return 2; // Establece un orden posterior al de FacultadSeeder
    }

    @Autowired
    private ModuloRepo moduloRepo;
    @Autowired
    private FacultadRepo facultadRepo;

    @Override
    public void run(String... args) throws Exception {

        Modulo modulo1 = new Modulo();
        modulo1.setName("Modulo 236");
        modulo1.setLatitud(-17.776254436687648);
        modulo1.setLongitud(-63.19507196545601);
        modulo1.setFacultad(facultadRepo.getById(1));
        moduloRepo.save(modulo1);

        Modulo modulo2 = new Modulo();
        modulo2.setName("Modulo 225");
        modulo2.setLongitud(-63.194475173950195);
        modulo2.setLatitud(-17.775433275693377);
        modulo2.setFacultad(facultadRepo.getById(2));
        moduloRepo.save(modulo2);

    }
}