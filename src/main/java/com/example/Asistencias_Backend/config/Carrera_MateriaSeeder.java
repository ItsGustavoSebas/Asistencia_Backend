package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Carrera;
import com.example.Asistencias_Backend.entity.Materia;
import com.example.Asistencias_Backend.entity.Materia_Carrera;
import com.example.Asistencias_Backend.repository.CarreraRepo;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import com.example.Asistencias_Backend.repository.MateriaRepo;
import com.example.Asistencias_Backend.repository.Materia_CarreraRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component("Carrera_MateriaSeeder")
@DependsOn({"FacultadSeeder"})
public class Carrera_MateriaSeeder implements CommandLineRunner, Ordered {

    @Autowired
    private FacultadRepo facultadRepo;

    @Override
    public int getOrder() {
        return 3;
    }

    @Autowired
    private MateriaRepo materiaRepo;
    @Autowired
    private CarreraRepo carreraRepo;
    @Autowired
    private Materia_CarreraRepo materia_CarreraRepo;

    @Override
    public void run(String... args) throws Exception {
        Materia materia1 = new Materia();
        materia1.setSigla("MAT101");
        materia1.setName("Calculo 1");

        Carrera carrera1 = new Carrera();
        carrera1.setName("Ingenieria en Sistemas");
        carrera1.setFacultad(facultadRepo.findById(1).orElse(null));

        Carrera carrera2 = new Carrera();
        carrera2.setName("Ingenieria Informatica");
        carrera2.setFacultad(facultadRepo.findById(1).orElse(null));

        Carrera carrera3 = new Carrera();
        carrera3.setName("Ingenieria en Redes y Telecomunicaciones");
        carrera3.setFacultad(facultadRepo.findById(1).orElse(null));

        Materia_Carrera materiaCarrera1 = new Materia_Carrera();
        materiaCarrera1.setSemestre(1);
        materiaCarrera1.setMateria(materia1);
        materiaCarrera1.setCarrera(carrera1);

        Materia_Carrera materiaCarrera2 = new Materia_Carrera();
        materiaCarrera2.setSemestre(1);
        materiaCarrera2.setMateria(materia1);
        materiaCarrera2.setCarrera(carrera2);

        Materia_Carrera materiaCarrera3 = new Materia_Carrera();
        materiaCarrera3.setSemestre(1);
        materiaCarrera3.setMateria(materia1);
        materiaCarrera3.setCarrera(carrera3);

        Materia materia2 = new Materia();
        materia2.setSigla("INF110");
        materia2.setName("Introduccion a la Informatica");

        Materia_Carrera materiaCarrera4 = new Materia_Carrera();
        materiaCarrera4.setSemestre(1);
        materiaCarrera4.setMateria(materia2);
        materiaCarrera4.setCarrera(carrera1);

        Materia_Carrera materiaCarrera5 = new Materia_Carrera();
        materiaCarrera5.setSemestre(1);
        materiaCarrera5.setMateria(materia2);
        materiaCarrera5.setCarrera(carrera2);

        Materia_Carrera materiaCarrera6 = new Materia_Carrera();
        materiaCarrera6.setSemestre(1);
        materiaCarrera6.setMateria(materia2);
        materiaCarrera6.setCarrera(carrera3);

        materiaRepo.save(materia1);
        materiaRepo.save(materia2);
        carreraRepo.save(carrera1);
        carreraRepo.save(carrera2);
        carreraRepo.save(carrera3);
        materia_CarreraRepo.save(materiaCarrera1);
        materia_CarreraRepo.save(materiaCarrera2);
        materia_CarreraRepo.save(materiaCarrera3);
        materia_CarreraRepo.save(materiaCarrera4);
        materia_CarreraRepo.save(materiaCarrera5);
        materia_CarreraRepo.save(materiaCarrera6);

    }
}
