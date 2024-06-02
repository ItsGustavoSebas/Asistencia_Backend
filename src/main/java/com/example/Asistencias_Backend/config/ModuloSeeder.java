package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Aula;
import com.example.Asistencias_Backend.entity.Modulo;
import com.example.Asistencias_Backend.repository.AulaRepo;
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

    @Autowired
    private AulaRepo aulaRepo;

    @Override
    public void run(String... args) throws Exception {

        Modulo modulo1 = new Modulo();
        modulo1.setName("Modulo 236");
        modulo1.setFacultad(facultadRepo.getById(1));

        Aula aula1 = new Aula();
        aula1.setName("Aula 11");
        aula1.setModulo(modulo1);

        Aula aula2 = new Aula();
        aula2.setName("Aula 12");
        aula2.setModulo(modulo1);

        Aula aula3 = new Aula();
        aula3.setName("Aula 13");
        aula3.setModulo(modulo1);

        Aula aula4 = new Aula();
        aula4.setName("Aula 14");
        aula4.setModulo(modulo1);

        Aula aula5 = new Aula();
        aula5.setName("Aula 15");
        aula5.setModulo(modulo1);

        Aula aula6 = new Aula();
        aula6.setName("Aula 16");
        aula6.setModulo(modulo1);

        modulo1.setAulas(Arrays.asList(aula1, aula2, aula3, aula4, aula5, aula6));
        moduloRepo.save(modulo1);

        Modulo modulo2 = new Modulo();
        modulo2.setName("Modulo 225");
        modulo2.setFacultad(facultadRepo.getById(2));

        Aula aula7 = new Aula();
        aula7.setName("Aula 11");
        aula7.setModulo(modulo2);

        Aula aula8 = new Aula();
        aula8.setName("Aula 12");
        aula8.setModulo(modulo2);

        Aula aula9 = new Aula();
        aula9.setName("Aula 13");
        aula9.setModulo(modulo2);

        Aula aula10 = new Aula();
        aula10.setName("Aula 14");
        aula10.setModulo(modulo2);

        Aula aula11 = new Aula();
        aula11.setName("Aula 15");
        aula11.setModulo(modulo2);

        Aula aula12 = new Aula();
        aula12.setName("Aula 16");
        aula12.setModulo(modulo2);

        modulo2.setAulas(Arrays.asList(aula7, aula8, aula9, aula10, aula11, aula12));

        moduloRepo.save(modulo2);

    }
}