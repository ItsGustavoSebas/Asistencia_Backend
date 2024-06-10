package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.AsistenciaDTO;
import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.AsistenciaRepo;
import com.example.Asistencias_Backend.repository.LicenciaRepo;
import com.example.Asistencias_Backend.repository.Programacion_AcademicaRepo;
import com.example.Asistencias_Backend.repository.UsersRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AsistenciaService {

    @Autowired
    private Programacion_AcademicaRepo programacionAcademicaRepository;

    @Autowired
    private LicenciaRepo licenciaRepository;

    @Autowired
    private AsistenciaRepo asistenciaRepository;

    @Autowired
    private UsersRepo usersRepo;

    @Transactional
    public void generarAsistenciasDiarias() {
        System.out.println("Ejecutando generarAsistenciasDiarias...");

        List<Programacion_Academica> programaciones = programacionAcademicaRepository.findAll();
        LocalDate hoy = LocalDate.now();
        DayOfWeek diaDeHoy = hoy.getDayOfWeek();
        String diaActual = diaDeHoy.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

        diaActual = Normalizer.normalize(diaActual, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        diaActual = diaActual.substring(0, 1).toUpperCase() + diaActual.substring(1).toLowerCase();

        for (Programacion_Academica programacion : programaciones) {
            List<FechaImportante> fechasImportantes = programacion.getGrupo().getFacultadGestion().getFechaImportantes();
            boolean esDiaDeClase = false;
            boolean esDiaNoLaborable = false;

            for (FechaImportante fecha : fechasImportantes) {
                if (fecha.getTipo().getId() == 1 && !hoy.isBefore(fecha.getFechaInicio()) && !hoy.isAfter(fecha.getFechaFin())) {
                    esDiaDeClase = true;
                } else if (fecha.getTipo().getId() == 2 && !hoy.isBefore(fecha.getFechaInicio()) && !hoy.isAfter(fecha.getFechaFin())) {
                    esDiaNoLaborable = true;
                    break;
                }
            }

            if (esDiaNoLaborable) {
                System.out.println("Hoy es un día no laborable para la programación: " + programacion.getId());
                continue; // Saltar a la siguiente programación si hoy es un día no laborable
            }

            if (!esDiaDeClase) {
                System.out.println("Hoy no es un día de clase para la programación: " + programacion.getId());
                continue; // Saltar a la siguiente programación si hoy no está entre la fecha de inicio y fin de clases
            }

            if (programacion.getDiaHorario().getDia().getName().equalsIgnoreCase(diaActual)) {
                Asistencia asistencia = new Asistencia();
                asistencia.setFecha(LocalDateTime.now());
                asistencia.setProgramacionAcademica(programacion);
                asistencia.setLatitud(0.0);
                asistencia.setLongitud(0.0);

                Optional<Licencia> licencia = licenciaRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualAndProgramacionAcademica(hoy, hoy, programacion);
                if (licencia.isPresent()) {
                    asistencia.setEstado("Licencia");
                } else {
                    asistencia.setEstado("Falta"); // Por defecto, falta hasta que se marque como presente
                }

                asistenciaRepository.save(asistencia);
                System.out.println("Asistencia creada para la programación: " + programacion.getId());
            }
        }
    }
    public List<AsistenciaDTO> getAsistenciasByUserId(int userId) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<Grupo> grupos = user.getGrupos();

        List<AsistenciaDTO> asistenciasDTO = new ArrayList<>();

        for (Grupo grupo : grupos) {
            List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
            for (Programacion_Academica programacion : programaciones) {
                List<Asistencia> asistencias = programacion.getAsistencias();
                for (Asistencia asistencia : asistencias) {
                    asistencia.setProgramacionAcademica(null);
                    AsistenciaDTO asistenciaDTO = new AsistenciaDTO(asistencia, programacion);
                    asistenciasDTO.add(asistenciaDTO);
                }
            }
        }
        return asistenciasDTO;
    }
}
