package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Cargo;
import com.example.Asistencias_Backend.repository.CargoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component("CargoSeeder")
public class CargoSeeder implements CommandLineRunner, Ordered {

    @Override
    public int getOrder() {
        return 4; // Establece un orden posterior al de FacultadSeeder
    }

    @Autowired
    private CargoRepo cargoRepo;

    @Override
    public void run(String... args) throws Exception {
        Cargo cargo1 = new Cargo();
        cargo1.setName("Rector");
        cargoRepo.save(cargo1);

        Cargo cargo2 = new Cargo();
        cargo2.setName("Vice Rector");
        cargoRepo.save(cargo2);

        Cargo cargo3 = new Cargo();
        cargo3.setName("Docente");
        cargoRepo.save(cargo3);
    }
}
