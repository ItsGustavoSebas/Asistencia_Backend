package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component("Programacion_AcademicaSeeder")
@DependsOn({"FacultadSeeder"})
public class Programacion_AcademicaSeeder implements CommandLineRunner, Ordered {

    @Autowired
    private Programacion_AcademicaRepo programacion_AcademicaRepo;

    @Override
    public int getOrder() {
        return 9;
    }

    @Autowired
    private Dia_HorarioRepo diaHorarioRepo;

    @Autowired
    private GrupoRepo grupoRepo;

    @Autowired
    private ModuloRepo moduloRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private Facultad_GestionRepo facultadGestionRepo;

    @Autowired
    private Materia_CarreraRepo materiaCarreraRepo;

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        List<ProgramacionData> programacionDataList = Arrays.asList(
                new ProgramacionData("LIN100", "Z4", "ZUNIGA RUIZ WILMA",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "16:00", "18:15"),
                                new DiaHorarioData("Jueves", "16:00", "18:15")
                        )),
                new ProgramacionData("LIN100", "Z5", "CLAURE MEDRANO DE OROPEZA ELIZ",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "13:45", "16:00"),
                                new DiaHorarioData("Jueves", "13:45", "16:00")
                        )),
                new ProgramacionData("MAT101", "F1", "AVENDANO GONZALES EUDAL",
                        Arrays.asList(
                                new DiaHorarioData("Miercoles", "18:15", "20:30"),
                                new DiaHorarioData("Viernes", "18:15", "20:30")
                        )),
                new ProgramacionData("MAT101", "H", "TEJERINA GUERRA JULIO",
                        Arrays.asList(
                                new DiaHorarioData("Miercoles", "07:00", "09:15"),
                                new DiaHorarioData("Viernes", "07:00", "09:15"),
                                new DiaHorarioData("Sabado", "08:30", "10:00")
                        )),
                new ProgramacionData("MAT101", "SB", "JUSTINIANO VACA JUAN TOMAS",
                        Arrays.asList(
                                new DiaHorarioData("Lunes", "08:30", "10:00"),
                                new DiaHorarioData("Miercoles", "08:30", "10:00"),
                                new DiaHorarioData("Viernes", "08:30", "10:00")
                        )),
                new ProgramacionData("MAT101", "BF", "MORALES MENDEZ MAGALY",
                        Arrays.asList(
                                new DiaHorarioData("Lunes", "08:30", "10:00"),
                                new DiaHorarioData("Miercoles", "08:30", "10:00"),
                                new DiaHorarioData("Viernes", "08:30", "10:00")
                        )),
                new ProgramacionData("MAT101", "SG", "CALIZAYA AJHUACHO MAGNO EDWIN",
                        Arrays.asList(
                                new DiaHorarioData("Viernes", "12:15", "14:30"),
                                new DiaHorarioData("Sábado", "08:30", "10:00")
                        )),
                new ProgramacionData("MAT101", "SI", "CALDERON FLORES MODESTO FRANKL",
                        Arrays.asList(
                                new DiaHorarioData("Lunes", "15:30", "17:00"),
                                new DiaHorarioData("Miercoles", "13:45", "16:00")
                        )),
                new ProgramacionData("MAT101", "SI", "NUNEZ ROMERO ERNESTO",
                        Arrays.asList(
                                new DiaHorarioData("Lunes", "13:45", "16:00"),
                                new DiaHorarioData("Miercoles", "13:45", "16:00")
                        )),
                new ProgramacionData("MAT101", "SJ", "ZABALA RUIZ HECTOR",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "07:00", "09:15"),
                                new DiaHorarioData("Jueves", "07:00", "09:15")
                        )),
                new ProgramacionData("MAT101", "SP", "DURAN CESPEDES BERTHY RONALD",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "07:00", "09:15"),
                                new DiaHorarioData("Jueves", "09:15", "11:30")
                        )),
                new ProgramacionData("MAT101", "SP", "JUSTINIANO VACA JUAN TOMAS",
                        Arrays.asList(
                                new DiaHorarioData("Jueves", "09:15", "11:30"),
                                new DiaHorarioData("Martes", "09:15", "11:30")
                        )),
                new ProgramacionData("MAT101", "SZ", "SILES MUNOZ FELIX",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "09:15", "11:30"),
                                new DiaHorarioData("Jueves", "09:15", "11:30")
                        )),
                new ProgramacionData("MAT101", "T2", "ABARCA SOTA NANCY",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "09:15", "11:30"),
                                new DiaHorarioData("Jueves", "09:15", "11:30")
                        )),
                new ProgramacionData("MAT101", "Z3", "MIRANDA CARRASCO CARLOS",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "16:00", "18:15"),
                                new DiaHorarioData("Jueves", "16:00", "18:15")
                        )),
                new ProgramacionData("MAT101", "Z4", "VALDEMOLAR ORELLANA TOMAS",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "13:45", "16:00"),
                                new DiaHorarioData("Jueves", "13:45", "16:00")
                        )),
                new ProgramacionData("MAT101", "Z5", "ACOSTA CABEZAS BARTOLO JAVIER",
                        Arrays.asList(
                                new DiaHorarioData("Jueves", "19:45", "21:15"),
                                new DiaHorarioData("Viernes", "19:45", "21:15")
                        )),
                new ProgramacionData("FIS102", "BI", "ZEBALLOS PAREDES DANIEL LUIS",
                        Arrays.asList(
                                new DiaHorarioData("Martes", "07:00", "09:15"),
                                new DiaHorarioData("Jueves", "07:00", "09:15")
                        )),
                new ProgramacionData("FIS102", "NF", "CALDERON FLORES MODESTO FRANKL",
                        Arrays.asList(
                                new DiaHorarioData("Miercoles", "18:15", "20:30"),
                                new DiaHorarioData("Viernes", "18:15", "20:30"),
                                new DiaHorarioData("Sabado", "18:15", "20:30")
                        )),
                new ProgramacionData("FIS102", "NW", "ZEBALLOS PAREDES DANIEL LUIS",
                        Arrays.asList(
                                new DiaHorarioData("Miercoles", "07:00", "09:15"),
                                new DiaHorarioData("Viernes", "07:00", "09:15"),
                                new DiaHorarioData("Jueves", "09:15", "11:30")
                        )),
                new ProgramacionData("FIS102", "SB", "ZEBALLOS PAREDES DANIEL LUIS",
                        Arrays.asList(
                                new DiaHorarioData("Lunes", "13:45", "15:15"),
                                new DiaHorarioData("Miercoles", "13:45", "15:15"),
                                new DiaHorarioData("Viernes", "13:45", "15:15"),
                                new DiaHorarioData("Viernes", "16:45", "18:15")
                        ))

        );
        Modulo modulo = moduloRepo.findById(1).orElse(null);
        for (ProgramacionData data : programacionDataList) {
            Grupo grupo = new Grupo();
            grupo.setName(data.getGrupo());
            grupo.setDocente(usersRepo.findByName(data.getDocente()));
            grupo.setFacultadGestion(facultadGestionRepo.findById(1).orElse(null));
            grupo.setMateriaCarrera(materiaCarreraRepo.findByMateria_SiglaAndCarrera_Name(data.getSigla(), "Ingenieria en Sistemas"));
            grupoRepo.save(grupo);

            for (DiaHorarioData diaHorarioData : data.getDiaHorarios()) {
                Dia_Horario diaHorario = diaHorarioRepo.findByDia_NameAndHorario_HoraInicioAndHorario_HoraFin(
                        diaHorarioData.getDia(),
                        LocalTime.parse(diaHorarioData.getHoraInicio(), timeFormatter),
                        LocalTime.parse(diaHorarioData.getHoraFin(), timeFormatter)
                );

                if (diaHorario == null) {
                    System.out.println("No se encontró Dia_Horario para: " + diaHorarioData);
                    continue;
                }

                Programacion_Academica programacionAcademica = new Programacion_Academica();
                programacionAcademica.setGrupo(grupo);
                programacionAcademica.setDiaHorario(diaHorario);
                programacionAcademica.setAula(10);
                programacionAcademica.setModulo(modulo);
                programacion_AcademicaRepo.save(programacionAcademica);
            }
        }


    }

    static class ProgramacionData {
        private final String sigla;
        private final String grupo;
        private final String docente;
        private final List<DiaHorarioData> diaHorarios;

        public ProgramacionData(String sigla, String grupo, String docente, List<DiaHorarioData> diaHorarios) {
            this.sigla = sigla;
            this.grupo = grupo;
            this.docente = docente;
            this.diaHorarios = diaHorarios;
        }

        public String getSigla() {
            return sigla;
        }

        public String getGrupo() {
            return grupo;
        }

        public String getDocente() {
            return docente;
        }

        public List<DiaHorarioData> getDiaHorarios() {
            return diaHorarios;
        }
    }

    static class DiaHorarioData {
        private final String dia;
        private final String horaInicio;
        private final String horaFin;

        public DiaHorarioData(String dia, String horaInicio, String horaFin) {
            this.dia = dia;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
        }

        public String getDia() {
            return dia;
        }

        public String getHoraInicio() {
            return horaInicio;
        }

        public String getHoraFin() {
            return horaFin;
        }

        @Override
        public String toString() {
            return "DiaHorarioData{" +
                    "dia='" + dia + '\'' +
                    ", horaInicio='" + horaInicio + '\'' +
                    ", horaFin='" + horaFin + '\'' +
                    '}';
        }
    }
}
