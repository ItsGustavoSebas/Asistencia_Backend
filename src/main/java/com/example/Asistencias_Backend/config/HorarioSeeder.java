package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Dia;
import com.example.Asistencias_Backend.entity.Dia_Horario;
import com.example.Asistencias_Backend.entity.Horario;
import com.example.Asistencias_Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component("HorarioSeeder")
@DependsOn({"FacultadSeeder"})
public class HorarioSeeder implements CommandLineRunner, Ordered {

    @Override
    public int getOrder() {
        return 8;
    }

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private Dia_HorarioRepo diaHorarioRepo;

    @Autowired
    private DiaRepo diaRepo;

    @Autowired
    private HorarioRepo horarioRepo;

    @Override
    public void run(String... args) throws Exception {
        Dia lunes = new Dia();
        lunes.setName("Lunes");
        diaRepo.save(lunes);

        Dia martes = new Dia();
        martes.setName("Martes");
        diaRepo.save(martes);

        Dia miercoles = new Dia();
        miercoles.setName("Miercoles");
        diaRepo.save(miercoles);

        Dia jueves = new Dia();
        jueves.setName("Jueves");
        diaRepo.save(jueves);

        Dia viernes = new Dia();
        viernes.setName("Viernes");
        diaRepo.save(viernes);

        Dia sabado = new Dia();
        sabado.setName("Sabado");
        diaRepo.save(sabado);

        // Creación de horarios únicos
        Map<String, Horario> horariosMap = new HashMap<>();

        String[] horarios = {
                "07:00-09:15", "09:15-11:30", "11:30-13:00", "13:45-16:00",
                "16:00-18:15", "18:15-20:30", "07:00-08:30", "08:30-10:00",
                "10:00-11:30", "13:00-14:30", "15:15-16:45", "18:45-21:15",
                "13:45-15:15", "18:15-19:45", "19:45-21:15", "20:30-22:45",
                "14:30-16:45", "12:15-14:30", "16:45-18:15", "17:30-19:45",
                "20:30-22:00", "08:00-09:30", "15:30-17:00"
        };

        for (String rango : horarios) {
            String[] partes = rango.split("-");
            LocalTime inicio = LocalTime.parse(partes[0], timeFormatter);
            LocalTime fin = LocalTime.parse(partes[1], timeFormatter);

            Horario horario = new Horario();
            horario.setHoraInicio(inicio);
            horario.setHoraFin(fin);
            horarioRepo.save(horario);

            horariosMap.put(rango, horario);
        }

        String[][] diaHorarios = {
                {"Lunes", "07:00-09:15", "08:30-10:00", "09:15-11:30", "12:15-14:30", "13:45-16:00", "16:45-18:15", "19:45-21:15", "14:30-16:45", "18:15-20:30", "20:30-22:00", "13:45-15:15", "07:00-08:30", "20:30-22:45", "10:00-11:30", "11:30-13:00", "08:00-09:30", "15:30-17:00"},
                {"Martes", "07:00-09:15", "09:15-11:30", "12:15-14:30", "13:45-16:00", "16:00-18:15", "18:15-20:30", "19:45-21:15", "17:30-19:45", "16:45-18:15", "14:30-16:45", "20:30-22:45", "10:00-11:30", "07:00-08:30", "11:30-13:00", "08:00-09:30"},
                {"Miercoles", "07:00-09:15", "08:30-10:00", "09:15-11:30", "13:45-16:00", "16:00-18:15", "19:45-21:15", "20:30-22:45", "16:45-18:15", "14:30-16:45", "18:15-20:30", "20:30-22:00", "13:45-15:15", "07:00-08:30", "10:00-11:30", "11:30-13:00", "08:00-09:30", "12:15-14:30", "15:30-17:00"},
                {"Jueves", "07:00-09:15", "08:30-10:00", "09:15-11:30", "13:45-16:00", "16:00-18:15", "18:15-20:30", "19:45-21:15", "17:30-19:45", "16:45-18:15", "14:30-16:45", "20:30-22:45", "10:00-11:30", "11:30-13:00", "08:00-09:30", "12:15-14:30"},
                {"Viernes", "07:00-08:30", "08:30-10:00", "10:00-11:30", "13:45-16:00", "16:45-18:15", "14:30-16:45", "18:15-20:30", "20:30-22:00", "13:45-15:15", "20:30-22:45", "11:30-13:00", "08:00-09:30", "07:00-09:15", "19:45-21:15", "12:15-14:30", "15:30-17:00"},
                {"Sabado", "07:00-08:30", "08:30-10:00", "16:45-18:15", "18:15-20:30", "13:45-15:15"}
        };

        for (String[] diaHorario : diaHorarios) {
            Dia dia = diaRepo.findByName(diaHorario[0]);
            System.out.println("Dia encontrado: " + dia.getName());
            for (int i = 1; i < diaHorario.length; i++) {
                String rango = diaHorario[i];
                Horario horario = horariosMap.get(rango);
                System.out.println("Horario encontrado: " + horario.getHoraInicio() + " - " + horario.getHoraFin());

                Dia_Horario diaHorarioEntity = new Dia_Horario();
                diaHorarioEntity.setDia(dia);
                diaHorarioEntity.setHorario(horario);
                diaHorarioRepo.save(diaHorarioEntity);
            }
        }
    }

}
