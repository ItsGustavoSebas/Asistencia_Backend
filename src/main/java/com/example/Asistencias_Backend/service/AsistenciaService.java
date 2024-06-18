package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.AsistenciaDTO;
import com.example.Asistencias_Backend.dto.AsistenciaUsuarioDTO;
import com.example.Asistencias_Backend.dto.AsistenciasDTO;
import com.example.Asistencias_Backend.dto.UsuarioEstadoAsistenciasDTO;
import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.text.Normalizer;
import java.time.format.TextStyle;

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

    @Autowired
    private GrupoRepo grupoRepo;

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

                // Combinar la fecha actual con la hora de inicio
                LocalTime horaInicio = programacion.getDiaHorario().getHorario().getHoraInicio();
                LocalDateTime fechaConHoraInicio = hoy.atTime(horaInicio);

                asistencia.setFecha(fechaConHoraInicio);
                asistencia.setProgramacionAcademica(programacion);
                asistencia.setLatitud(0.0);
                asistencia.setLongitud(0.0);
                OurUsers ourUsers = programacion.getGrupo().getDocente();
                Optional<Licencia> licencia = licenciaRepository.findByFechaInicioLessThanEqualAndFechaFinGreaterThanEqualAndOurUsers(hoy, hoy, ourUsers);
                if (licencia.isPresent()) {
                    asistencia.setEstado("Licencia");
                } else {
                    asistencia.setEstado("Falta");
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

        LocalDateTime now = LocalDateTime.now();

        for (Grupo grupo : grupos) {
            List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
            for (Programacion_Academica programacion : programaciones) {
                List<Asistencia> asistencias = programacion.getAsistencias();
                for (Asistencia asistencia : asistencias) {
                    if (asistencia.getFecha().isBefore(now)) {
                        asistencia.setProgramacionAcademica(null);
                        AsistenciaDTO asistenciaDTO = new AsistenciaDTO(asistencia, programacion);
                        asistenciasDTO.add(asistenciaDTO);
                    }
                }
            }
        }

        asistenciasDTO.sort(Comparator.comparing(AsistenciaDTO::getFecha));
        return asistenciasDTO;
    }



    public List<AsistenciaDTO> getAsistencias() {

        List<Grupo> grupos = grupoRepo.findAll();

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

    public List<AsistenciaUsuarioDTO> getAllUsersAsistenciasDate(Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findAll();

        List<AsistenciaUsuarioDTO> usuariosAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos();

            List<AsistenciaDTO> asistenciasDTO = new ArrayList<>();

            for (Grupo grupo : grupos) {
                List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
                for (Programacion_Academica programacion : programaciones) {
                    List<Asistencia> asistencias = programacion.getAsistencias();
                    for (Asistencia asistencia : asistencias) {
                        LocalDateTime fechaAsistencia = asistencia.getFecha();
                        if (!fechaAsistencia.isBefore(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                                && !fechaAsistencia.isAfter(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23).withMinute(59).withSecond(59))) {
                            asistencia.setProgramacionAcademica(null);
                            AsistenciaDTO asistenciaDTO = new AsistenciaDTO(asistencia, programacion);
                            asistenciasDTO.add(asistenciaDTO);
                        }
                    }
                }
            }

            AsistenciaUsuarioDTO usuarioAsistenciasDTO = new AsistenciaUsuarioDTO(user.getName(), user.getId(), asistenciasDTO);
            usuariosAsistenciasDTO.add(usuarioAsistenciasDTO);
        }

        return usuariosAsistenciasDTO;
    }

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasDate(Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findByRoles_Name("USER");

        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos();
            if (!grupos.isEmpty()) {
                int faltas = 0;
                int asistencias = 0;
                int licencias = 0;
                int atrasos = 0;
                int numGrupos = grupos.size();
                int numProgramaciones = 0;
                for (Grupo grupo : grupos) {
                    List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
                    numProgramaciones += programaciones.size();
                    for (Programacion_Academica programacion : programaciones) {
                        List<Asistencia> asistenciasList = programacion.getAsistencias();
                        for (Asistencia asistencia : asistenciasList) {
                            LocalDateTime fechaAsistencia = asistencia.getFecha();
                            if (!fechaAsistencia.isBefore(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                                    && !fechaAsistencia.isAfter(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23).withMinute(59).withSecond(59))
                                    && fechaAsistencia.isBefore(LocalDateTime.now())) {
                                if (asistencia.getEstado().equals("Falta")) {
                                    faltas++;
                                } else if (asistencia.getEstado().equals("Presente")) {
                                    asistencias++;
                                } else if (asistencia.getEstado().equals("Licencia")) {
                                    licencias++;
                                } else if (asistencia.getEstado().equals("Atraso")) {
                                    atrasos++;
                                }
                            }
                        }
                    }
                }
                UsuarioEstadoAsistenciasDTO usuarioEstadoAsistenciasDTO = new UsuarioEstadoAsistenciasDTO(user.getId(), user.getName(), faltas, asistencias, licencias, atrasos, numGrupos, numProgramaciones);
                usuariosEstadoAsistenciasDTO.add(usuarioEstadoAsistenciasDTO);
            }
        }
        return usuariosEstadoAsistenciasDTO;
    }

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasNameDate(String name, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findByRoles_NameAndNameContainingIgnoreCase("USER", name);
        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos();
            if (!grupos.isEmpty()) {
                int faltas = 0;
                int asistencias = 0;
                int licencias = 0;
                int atrasos = 0;
                int numGrupos = grupos.size();
                int numProgramaciones = 0;
                for (Grupo grupo : grupos) {
                    List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
                    numProgramaciones += programaciones.size();
                    for (Programacion_Academica programacion : programaciones) {
                        List<Asistencia> asistenciasList = programacion.getAsistencias();
                        for (Asistencia asistencia : asistenciasList) {
                            LocalDateTime fechaAsistencia = asistencia.getFecha();
                            if (!fechaAsistencia.isBefore(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                                    && !fechaAsistencia.isAfter(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23).withMinute(59).withSecond(59))
                                    && fechaAsistencia.isBefore(LocalDateTime.now())) {
                                if (asistencia.getEstado().equals("Falta")) {
                                    faltas++;
                                } else if (asistencia.getEstado().equals("Presente")) {
                                    asistencias++;
                                } else if (asistencia.getEstado().equals("Licencia")) {
                                    licencias++;
                                } else if (asistencia.getEstado().equals("Atraso")) {
                                    atrasos++;
                                }
                            }
                        }
                    }
                }
                UsuarioEstadoAsistenciasDTO usuarioEstadoAsistenciasDTO = new UsuarioEstadoAsistenciasDTO(user.getId(), user.getName(), faltas, asistencias, licencias, atrasos, numGrupos, numProgramaciones);
                usuariosEstadoAsistenciasDTO.add(usuarioEstadoAsistenciasDTO);
            }
        }
        return usuariosEstadoAsistenciasDTO;
    }



    public List<AsistenciaUsuarioDTO> getAllUsersAsistencias() {
        List<OurUsers> users = usersRepo.findAll();

        List<AsistenciaUsuarioDTO> usuariosAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
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

            AsistenciaUsuarioDTO usuarioAsistenciasDTO = new AsistenciaUsuarioDTO(user.getName(), user.getId(), asistenciasDTO);
            usuariosAsistenciasDTO.add(usuarioAsistenciasDTO);
        }

        return usuariosAsistenciasDTO;
    }

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistencias() {
        List<OurUsers> users = usersRepo.findByRoles_Name("USER");
        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos();
            if (!grupos.isEmpty()) {
                int faltas = 0;
                int asistencias = 0;
                int licencias = 0;
                int atrasos = 0;
                int numGrupos = grupos.size();
                int numProgramaciones = 0;
                for (Grupo grupo : grupos) {
                    List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
                    numProgramaciones += programaciones.size();
                    for (Programacion_Academica programacion : programaciones) {
                        List<Asistencia> asistenciasList = programacion.getAsistencias();
                        for (Asistencia asistencia : asistenciasList) {
                            LocalDateTime fechaAsistencia = asistencia.getFecha();
                            if (fechaAsistencia.isBefore(LocalDateTime.now())) {
                                if (asistencia.getEstado().equals("Falta")) {
                                    faltas++;
                                } else if (asistencia.getEstado().equals("Presente")) {
                                    asistencias++;
                                } else if (asistencia.getEstado().equals("Licencia")) {
                                    licencias++;
                                } else if (asistencia.getEstado().equals("Atraso")) {
                                    atrasos++;
                                }
                            }
                        }
                    }
                }
                UsuarioEstadoAsistenciasDTO usuarioEstadoAsistenciasDTO = new UsuarioEstadoAsistenciasDTO(user.getId(), user.getName(), faltas, asistencias, licencias, atrasos, numGrupos, numProgramaciones);
                usuariosEstadoAsistenciasDTO.add(usuarioEstadoAsistenciasDTO);
            }
        }
        return usuariosEstadoAsistenciasDTO;
    }

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasName(String name) {
        List<OurUsers> users = usersRepo.findByNameContainingIgnoreCase(name);

        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos();
            if (!grupos.isEmpty()) {
                int faltas = 0;
                int asistencias = 0;
                int licencias = 0;
                int atrasos = 0;
                int numGrupos = grupos.size();
                int numProgramaciones = 0;
                for (Grupo grupo : grupos) {
                    List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
                    numProgramaciones += programaciones.size();
                    for (Programacion_Academica programacion : programaciones) {
                        List<Asistencia> asistenciasList = programacion.getAsistencias();
                        for (Asistencia asistencia : asistenciasList) {
                            LocalDateTime fechaAsistencia = asistencia.getFecha();
                            if (fechaAsistencia.isBefore(LocalDateTime.now())) {
                                if (asistencia.getEstado().equals("Falta")) {
                                    faltas++;
                                } else if (asistencia.getEstado().equals("Presente")) {
                                    asistencias++;
                                } else if (asistencia.getEstado().equals("Licencia")) {
                                    licencias++;
                                } else if (asistencia.getEstado().equals("Atraso")) {
                                    atrasos++;
                                }
                            }
                        }
                    }
                }
                UsuarioEstadoAsistenciasDTO usuarioEstadoAsistenciasDTO = new UsuarioEstadoAsistenciasDTO(user.getId(), user.getName(), faltas, asistencias, licencias, atrasos, numGrupos, numProgramaciones);
                usuariosEstadoAsistenciasDTO.add(usuarioEstadoAsistenciasDTO);
            }
        }
        return usuariosEstadoAsistenciasDTO;
    }

    public List<AsistenciasDTO> getAsistenciasByUserIdWeb(int userId) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<Grupo> grupos = user.getGrupos();

        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();

        for (Grupo grupo : grupos) {
            List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
            for (Programacion_Academica programacion : programaciones) {
                List<Asistencia> asistencias = programacion.getAsistencias();
                for (Asistencia asistencia : asistencias) {
                    LocalDateTime fechaAsistencia = asistencia.getFecha();
                    if (fechaAsistencia.isBefore(LocalDateTime.now())) {
                        asistencia.setProgramacionAcademica(null);
                        AsistenciasDTO asistenciaDTO = new AsistenciasDTO();
                        asistenciaDTO.setNombre(programacion.getGrupo().getDocente().getName());
                        asistenciaDTO.setId(asistencia.getId());
                        asistenciaDTO.setEstado(asistencia.getEstado());
                        asistenciaDTO.setFecha(asistencia.getFecha());
                        asistenciaDTO.setHora(asistencia.getFecha());
                        asistenciaDTO.setMateriaSigla(programacion.getGrupo().getMateriaCarrera().getMateria().getSigla());
                        asistenciaDTO.setGrupoName(programacion.getGrupo().getName());
                        asistenciaDTO.setHoraInicio(programacion.getDiaHorario().getHorario().getHoraInicio());
                        asistenciaDTO.setHoraFin(programacion.getDiaHorario().getHorario().getHoraFin());
                        asistenciasDTO.add(asistenciaDTO);
                    }
                }
            }
        }
        asistenciasDTO.sort(Comparator.comparing(AsistenciasDTO::getFecha));
        return asistenciasDTO;
    }

    public List<AsistenciasDTO> getAsistenciasByUserIdDate(int userId, Date startDate, Date endDate) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<Grupo> grupos = user.getGrupos();

        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();

        for (Grupo grupo : grupos) {
            List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
            for (Programacion_Academica programacion : programaciones) {
                List<Asistencia> asistencias = programacion.getAsistencias();
                for (Asistencia asistencia : asistencias) {
                    LocalDateTime fechaAsistencia = asistencia.getFecha();
                    if (!fechaAsistencia.isBefore(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                            && !fechaAsistencia.isAfter(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23).withMinute(59).withSecond(59))
                            && fechaAsistencia.isBefore(LocalDateTime.now())) {
                    asistencia.setProgramacionAcademica(null);
                    AsistenciasDTO asistenciaDTO = new AsistenciasDTO();
                    asistenciaDTO.setId(asistencia.getId());
                    asistenciaDTO.setNombre(programacion.getGrupo().getDocente().getName());
                    asistenciaDTO.setEstado(asistencia.getEstado());
                    asistenciaDTO.setFecha(asistencia.getFecha());
                    asistenciaDTO.setHora(asistencia.getFecha());
                    asistenciaDTO.setMateriaSigla(programacion.getGrupo().getMateriaCarrera().getMateria().getSigla());
                    asistenciaDTO.setGrupoName(programacion.getGrupo().getName());
                    asistenciaDTO.setHoraInicio(programacion.getDiaHorario().getHorario().getHoraInicio());
                    asistenciaDTO.setHoraFin(programacion.getDiaHorario().getHorario().getHoraFin());
                    asistenciasDTO.add(asistenciaDTO);
                }
                }
            }
        }
        asistenciasDTO.sort(Comparator.comparing(AsistenciasDTO::getFecha));
        return asistenciasDTO;
    }

    public List<AsistenciasDTO> getAsistenciasByUserIdEstadoDate(int userId, String estado, Date startDate, Date endDate) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<Grupo> grupos = user.getGrupos();

        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();

        for (Grupo grupo : grupos) {
            List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
            for (Programacion_Academica programacion : programaciones) {
                List<Asistencia> asistencias = programacion.getAsistencias();
                for (Asistencia asistencia : asistencias) {
                    LocalDateTime fechaAsistencia = asistencia.getFecha();
                    if (asistencia.getEstado().equals(estado)
                            && !fechaAsistencia.isBefore(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                            && !fechaAsistencia.isAfter(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23).withMinute(59).withSecond(59))
                            && fechaAsistencia.isBefore(LocalDateTime.now())) {
                        asistencia.setProgramacionAcademica(null);
                        AsistenciasDTO asistenciaDTO = new AsistenciasDTO();
                        asistenciaDTO.setNombre(programacion.getGrupo().getDocente().getName());
                        asistenciaDTO.setId(asistencia.getId());
                        asistenciaDTO.setEstado(asistencia.getEstado());
                        asistenciaDTO.setFecha(asistencia.getFecha());
                        asistenciaDTO.setHora(asistencia.getFecha());
                        asistenciaDTO.setMateriaSigla(programacion.getGrupo().getMateriaCarrera().getMateria().getSigla());
                        asistenciaDTO.setGrupoName(programacion.getGrupo().getName());
                        asistenciaDTO.setHoraInicio(programacion.getDiaHorario().getHorario().getHoraInicio());
                        asistenciaDTO.setHoraFin(programacion.getDiaHorario().getHorario().getHoraFin());
                        asistenciasDTO.add(asistenciaDTO);
                    }
                }
            }
        }
        asistenciasDTO.sort(Comparator.comparing(AsistenciasDTO::getFecha));
        return asistenciasDTO;
    }

    public List<AsistenciasDTO> getAsistenciasByUserIdEstado(int userId, String estado) {
        OurUsers user = usersRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        List<Grupo> grupos = user.getGrupos();

        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();

        for (Grupo grupo : grupos) {
            List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
            for (Programacion_Academica programacion : programaciones) {
                List<Asistencia> asistencias = programacion.getAsistencias();
                for (Asistencia asistencia : asistencias) {
                    LocalDateTime fechaAsistencia = asistencia.getFecha();
                    if (asistencia.getEstado().equals(estado) && fechaAsistencia.isBefore(LocalDateTime.now())) {
                        asistencia.setProgramacionAcademica(null);
                        AsistenciasDTO asistenciaDTO = new AsistenciasDTO();
                        asistenciaDTO.setNombre(programacion.getGrupo().getDocente().getName());
                        asistenciaDTO.setId(asistencia.getId());
                        asistenciaDTO.setEstado(asistencia.getEstado());
                        asistenciaDTO.setFecha(asistencia.getFecha());
                        asistenciaDTO.setHora(asistencia.getFecha());
                        asistenciaDTO.setMateriaSigla(programacion.getGrupo().getMateriaCarrera().getMateria().getSigla());
                        asistenciaDTO.setGrupoName(programacion.getGrupo().getName());
                        asistenciaDTO.setHoraInicio(programacion.getDiaHorario().getHorario().getHoraInicio());
                        asistenciaDTO.setHoraFin(programacion.getDiaHorario().getHorario().getHoraFin());
                        asistenciasDTO.add(asistenciaDTO);
                    }
                }
            }
        }
        asistenciasDTO.sort(Comparator.comparing(AsistenciasDTO::getFecha));
        return asistenciasDTO;
    }

}
