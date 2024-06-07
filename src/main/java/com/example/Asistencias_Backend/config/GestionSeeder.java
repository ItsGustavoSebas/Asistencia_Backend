package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Facultad_Gestion;
import com.example.Asistencias_Backend.entity.FechaImportante;
import com.example.Asistencias_Backend.entity.Gestion;
import com.example.Asistencias_Backend.entity.Tipo;
import com.example.Asistencias_Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component("GestionSeeder")
@DependsOn({"FacultadSeeder"})
public class GestionSeeder implements CommandLineRunner, Ordered {

    @Override
    public int getOrder() {
        return 7;
    }

    @Autowired
    private FacultadRepo facultadRepo;

    @Autowired
    private GestionRepo gestionRepo;

    @Autowired
    private Facultad_GestionRepo facultadGestionRepo;

    @Autowired
    private Fecha_ImportanteRepo fechaImportanteRepo;

    @Autowired
    private TipoRepo tipoRepo;

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Gestion gestion1 = new Gestion();
        gestion1.setName("1-2024");
        gestionRepo.save(gestion1);

        Tipo tipo1 = new Tipo();
        tipo1.setName("inicio y final de clases");
        tipoRepo.save(tipo1);

        Tipo tipo2 = new Tipo();
        tipo2.setName("dia no laborable");
        tipoRepo.save(tipo2);

        Tipo tipo3 = new Tipo();
        tipo3.setName("conmemoracion");
        tipoRepo.save(tipo3);

        Gestion gestion2 = new Gestion();
        gestion2.setName("2-2024");
        gestionRepo.save(gestion2);

        Facultad_Gestion facultadGestion1 = new Facultad_Gestion();
        facultadGestion1.setFacultad(facultadRepo.findById(1).orElse(null));
        facultadGestion1.setGestion(gestion1);
        facultadGestionRepo.save(facultadGestion1);

        FechaImportante fechaImportante1 = new FechaImportante();
        fechaImportante1.setFechaInicio(LocalDate.parse("04-03-2024",formatter));
        fechaImportante1.setFechaFin(LocalDate.parse("12-08-2024",formatter));
        fechaImportante1.setDescripcion("inicio y fin de clases");
        fechaImportante1.setFacultadGestion(facultadGestion1);
        fechaImportante1.setTipo(tipo1);
        fechaImportanteRepo.save(fechaImportante1);

        FechaImportante fechaImportante2 = new FechaImportante();
        fechaImportante2.setFechaInicio(LocalDate.parse("01-05-2024",formatter));
        fechaImportante2.setFechaFin(LocalDate.parse("01-05-2024",formatter));
        fechaImportante2.setDescripcion("Dia del trabajo");
        fechaImportante2.setFacultadGestion(facultadGestion1);
        fechaImportante2.setTipo(tipo2);
        fechaImportanteRepo.save(fechaImportante2);

        FechaImportante fechaImportante3 = new FechaImportante();
        fechaImportante3.setFechaInicio(LocalDate.parse("30-05-2024",formatter));
        fechaImportante3.setFechaFin(LocalDate.parse("30-05-2024",formatter));
        fechaImportante3.setDescripcion("Festividad de Corpus Christi");
        fechaImportante3.setFacultadGestion(facultadGestion1);
        fechaImportante3.setTipo(tipo3);
        fechaImportanteRepo.save(fechaImportante3);

        FechaImportante fechaImportante5 = new FechaImportante();
        fechaImportante5.setFechaInicio(LocalDate.parse("21-06-2024",formatter));
        fechaImportante5.setFechaFin(LocalDate.parse("21-06-2024",formatter));
        fechaImportante5.setDescripcion("Festividad del Año Nuevo Aymara");
        fechaImportante5.setFacultadGestion(facultadGestion1);
        fechaImportante5.setTipo(tipo2);
        fechaImportanteRepo.save(fechaImportante5);

        FechaImportante fechaImportante4 = new FechaImportante();
        fechaImportante4.setFechaInicio(LocalDate.parse("06-06-2024",formatter));
        fechaImportante4.setFechaFin(LocalDate.parse("06-06-2024",formatter));
        fechaImportante4.setDescripcion("Dia del maestro");
        fechaImportante4.setFacultadGestion(facultadGestion1);
        fechaImportante4.setTipo(tipo2);
        fechaImportanteRepo.save(fechaImportante4);
    }
}