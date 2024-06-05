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

import java.util.Arrays;
import java.util.List;

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

        Carrera carrera1 = new Carrera();
        carrera1.setName("Ingenieria en Sistemas");
        carrera1.setFacultad(facultadRepo.findById(1).orElse(null));
        carreraRepo.save(carrera1);
        Carrera carrera2 = new Carrera();
        carrera2.setName("Ingenieria Informatica");
        carrera2.setFacultad(facultadRepo.findById(1).orElse(null));
        carreraRepo.save(carrera2);
        Carrera carrera3 = new Carrera();
        carrera3.setName("Ingenieria en Redes y Telecomunicaciones");
        carrera3.setFacultad(facultadRepo.findById(1).orElse(null));
        carreraRepo.save(carrera3);
        createMateriaCarrera("FIS100", "Fisica I", 1, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF110", "Introduccion A La Informatica", 1, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF119", "Estructuras Discretas", 1, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("LIN100", "Ingles Tecnico I", 1, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("MAT101", "Calculo I", 1, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("FIS102", "Fisica II", 2, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF120", "Programacion I", 2, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("LIN101", "Ingles Tecnico II", 2, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("MAT102", "Calculo II", 2, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("MAT103", "Algebra Lineal", 2, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("ADM100", "Administracion", 3, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("FIS200", "Fisica III", 3, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("ELT241", "Teoria de Campos", 3, Arrays.asList(carrera3));
        createMateriaCarrera("RDS210", "Análisis de Circuitos", 3, Arrays.asList(carrera3));
        createMateriaCarrera("INF210", "Programacion II", 3, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF211", "Arquitectura De Computadoras", 3, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("MAT207", "Ecuaciones Diferenciales", 3, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("RDS220", "Análisis de Circuitos Electronicos", 4, Arrays.asList(carrera3));
        createMateriaCarrera("ELT352", "Sistemas Logicos Y Digitales I", 5, Arrays.asList(carrera3));
        createMateriaCarrera("ELT354", "Senales Y Sistemas", 5, Arrays.asList(carrera3));
        createMateriaCarrera("RDS310", "Electronica Aplicada A Redes", 5, Arrays.asList(carrera3));
        createMateriaCarrera("ELT362", "Sistemas Logicos Y Digitales II", 6, Arrays.asList(carrera3));
        createMateriaCarrera("RDS320", "Interpretracion De Sistemas Y Senales", 6, Arrays.asList(carrera3));
        createMateriaCarrera("ELT374", "Sistemas De Comunicacion I", 7, Arrays.asList(carrera3));
        createMateriaCarrera("RDS410", "Aplicaciones Con Microprocesad.", 7, Arrays.asList(carrera3));
        createMateriaCarrera("ELT384", "Sistemas De Comunicacion II", 8, Arrays.asList(carrera3));
        createMateriaCarrera("RDS429", "Legislac.En Redes Y Comunicaciones", 8, Arrays.asList(carrera3));
        createMateriaCarrera("RDS421", "Taller De Analisis Y Diseno De Redes", 8, Arrays.asList(carrera3));
        createMateriaCarrera("RDS511", "Gestion Y Administracion De Redes", 9, Arrays.asList(carrera3));
        createMateriaCarrera("RDS512", "Redes Inalambric.Y Comunic.Moviles", 9, Arrays.asList(carrera3));
        createMateriaCarrera("RDS519", "Seguridad En Redes Y Transmis.De Datos", 9, Arrays.asList(carrera3));
        createMateriaCarrera("ADM200", "Contabilidad", 4, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF220", "Estructura De Datos I", 4, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF221", "Programacion Ensamblador", 4, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("MAT202", "Probabilidades Y Estadist.I", 4, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("MAT205", "Metodos Numericos", 4, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("ADM330", "Organizacion Y Metodos", 5, Arrays.asList(carrera1));
        createMateriaCarrera("ECO300", "Economia Para La Gestion", 5, Arrays.asList(carrera1));
        createMateriaCarrera("INF310", "Estructuras De Datos II", 5, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("INF312", "Bases De Datos I", 5, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("MAT302", "Probabilidades Y EstadísticaII", 5, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("ADM320", "Finanzas Para La Empresa", 6, Arrays.asList(carrera1));
        createMateriaCarrera("INF322", "Bases De Datos II", 6, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF323", "Sistemas Operativos I", 6, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF342", "Sistemas De Informacion I", 6, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("MAT329", "Investigacion Operativa I", 6, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF413", "Sistemas Operativos II", 7, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF412", "Sistemas De Informacion II", 7, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("INF433", "Redes I", 7, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF432", "Sistemas Para El Soporte A La Toma De Decisiones", 7, Arrays.asList(carrera1));
        createMateriaCarrera("MAT419", "Investigacion Operativa II", 7, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("ECO449", "Preparacion Y Evaluacion De Proyectos", 8, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF422", "Ingenieria De Software I", 8, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("INF423", "Redes II", 8, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF442", "Sistemas De Informacion Geografica", 8, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("INF462", "Auditoria Informatica", 8, Arrays.asList(carrera1));
        createMateriaCarrera("INF511", "Taller De Grado I", 9, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF512", "Ingenieria De Software II", 9, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("INF513", "Tecnologia Web", 9, Arrays.asList(carrera1, carrera2, carrera3));
        createMateriaCarrera("INF552", "Arquitectura Del Software", 9, Arrays.asList(carrera1, carrera2));
        createMateriaCarrera("INF318", "Programac.Logica Y Funcional", 5, Arrays.asList(carrera2));
        createMateriaCarrera("INF319", "Lenguajes Formales", 5, Arrays.asList(carrera2));
        createMateriaCarrera("INF329", "Compiladores", 6, Arrays.asList(carrera2));
        createMateriaCarrera("INF419", "Inteligencia Artificial", 7, Arrays.asList(carrera2));
        createMateriaCarrera("INF428", "Sistemas Expertos", 8, Arrays.asList(carrera2));
    }
    private void createMateriaCarrera(String sigla, String name, int semestre, List<Carrera> carreras) {
        Materia materia = new Materia();
        materia.setSigla(sigla);
        materia.setName(name);
        materiaRepo.save(materia);

        for (Carrera carrera : carreras) {
            Materia_Carrera materiaCarrera = new Materia_Carrera();
            materiaCarrera.setSemestre(semestre);
            materiaCarrera.setMateria(materia);
            materiaCarrera.setCarrera(carrera);
            materia_CarreraRepo.save(materiaCarrera);
        }
    }
}
