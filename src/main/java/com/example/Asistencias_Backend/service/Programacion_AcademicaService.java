package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.*;
import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Programacion_AcademicaService {

    @Autowired
    private Programacion_AcademicaRepo programacion_AcademicaRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private GrupoRepo grupoRepo;
    @Autowired
    private AsistenciaRepo asistenciaRepo;
    @Autowired
    private LicenciaRepo licenciaRepo;
    @Autowired
    private Materia_CarreraRepo materia_CarreraRepo;
    @Autowired
    private Facultad_GestionRepo facultad_GestionRepo;
    @Autowired
    private ModuloRepo moduloRepo;
    @Autowired
    private Dia_HorarioRepo dia_HorarioRepo;
    @Autowired
    private DiaRepo diaRepo;
    @Autowired
    private HorarioRepo horarioRepo;
    @Autowired
    private CarreraRepo carreraRepo;
    @Autowired
    private FacultadRepo facultadRepo;

    public ReqRes getProgramacion() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Grupo> result = grupoRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setGrupoList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No grupos found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public List<GrupoDTO> getGrupos() {
        List<GrupoDTO> grupoDTOS = new ArrayList<>();
        List<Grupo> grupos = grupoRepo.findAll();

        for (Grupo grupo : grupos) {
            GrupoDTO grupoDTO = new GrupoDTO();
            grupoDTO.setGrupoId(grupo.getId());
            grupoDTO.setNombre(grupo.getName());
            grupoDTO.setDocente(grupo.getDocente().getName());
            grupoDTO.setSemestre(grupo.getMateriaCarrera().getSemestre());
            grupoDTO.setSiglas(grupo.getMateriaCarrera().getMateria().getSigla());
            grupoDTO.setMateria(grupo.getMateriaCarrera().getMateria().getName());
            grupoDTO.setMateria_carreraId(grupo.getMateriaCarrera().getId());

            List<Dia_HorarioDTO> diaHorarioDTOS = new ArrayList<>();
            List<Programacion_Academica> programacionAcademicas = grupo.getProgramacionAcademicas();
            for (Programacion_Academica programacionAcademica : programacionAcademicas) {
                Dia_HorarioDTO diaHorarioDTO = new Dia_HorarioDTO();
                diaHorarioDTO.setDia(programacionAcademica.getDiaHorario().getDia().getName());
                diaHorarioDTO.setHoraInicio(programacionAcademica.getDiaHorario().getHorario().getHoraInicio());
                diaHorarioDTO.setHoraFin(programacionAcademica.getDiaHorario().getHorario().getHoraFin());
                diaHorarioDTO.setModulo(programacionAcademica.getModulo().getName());
                diaHorarioDTO.setAula(programacionAcademica.getAula());
                diaHorarioDTOS.add(diaHorarioDTO);
            }
            grupoDTO.setDiaHorarioDTOS(diaHorarioDTOS);
            grupoDTOS.add(grupoDTO);
        }
        grupoDTOS.sort(Comparator.comparing(GrupoDTO::getSemestre)
                .thenComparing(GrupoDTO::getSiglas)
                .thenComparing(GrupoDTO::getDocente));
        return grupoDTOS;
    }

    public ReqRes getCarrera_Materias(int carreraId) {
        ReqRes reqRes = new ReqRes();
        try {
            Carrera carrera = carreraRepo.findById(carreraId).orElseThrow(() -> new RuntimeException("Carrera Not found"));
            List<Materia_Carrera> result = carrera.getMateriasCarreras();
            if (!result.isEmpty()) {
                reqRes.setMateriaCarreras(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No materia found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes getCarreras(int facultadId) {
        ReqRes reqRes = new ReqRes();
        try {
            Facultad facultad = facultadRepo.findById(facultadId).orElseThrow(() -> new RuntimeException("Carrera Not found"));
            List<Carrera> result = facultad.getCarreras();
            if (!result.isEmpty()) {
                reqRes.setCarreraList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No carrera found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes getModulos(int facultadId) {
        ReqRes reqRes = new ReqRes();
        try {
            Facultad facultad = facultadRepo.findById(facultadId).orElseThrow(() -> new RuntimeException("Carrera Not found"));
            List<Modulo> result = facultad.getModulos();
            if (!result.isEmpty()) {
                reqRes.setModuloList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No carrera found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes getFacultad(int facultadId) {
        ReqRes reqRes = new ReqRes();
        try {
            Facultad_Gestion facultadGestion = facultad_GestionRepo.findById(facultadId).orElseThrow(() -> new RuntimeException("Carrera Not found"));
            Facultad result = facultadGestion.getFacultad();
            if (!result.getFacultadGestions().isEmpty()) {
                reqRes.setFacultad(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No carrera found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public List<GrupoDTO> getGruposFacultad(int facultadId) {
        List<GrupoDTO> grupoDTOS = new ArrayList<>();
        List<Grupo> grupos = grupoRepo.findAll();
        for (Grupo grupo : grupos) {
            if (grupo.getFacultadGestion().getId() == facultadId) {
                GrupoDTO grupoDTO = new GrupoDTO();
                grupoDTO.setGrupoId(grupo.getId());
                grupoDTO.setNombre(grupo.getName());
                grupoDTO.setDocente(grupo.getDocente().getName());
                grupoDTO.setSemestre(grupo.getMateriaCarrera().getSemestre());
                grupoDTO.setSiglas(grupo.getMateriaCarrera().getMateria().getSigla());
                grupoDTO.setMateria(grupo.getMateriaCarrera().getMateria().getName());
                grupoDTO.setMateria_carreraId(grupo.getMateriaCarrera().getId());
                List<Dia_HorarioDTO> diaHorarioDTOS = new ArrayList<>();
                List<Programacion_Academica> programacionAcademicas = grupo.getProgramacionAcademicas();
                for (Programacion_Academica programacionAcademica : programacionAcademicas) {
                    Dia_HorarioDTO diaHorarioDTO = new Dia_HorarioDTO();
                    diaHorarioDTO.setDia(programacionAcademica.getDiaHorario().getDia().getName());
                    diaHorarioDTO.setHoraInicio(programacionAcademica.getDiaHorario().getHorario().getHoraInicio());
                    diaHorarioDTO.setHoraFin(programacionAcademica.getDiaHorario().getHorario().getHoraFin());
                    diaHorarioDTO.setModulo(programacionAcademica.getModulo().getName());
                    diaHorarioDTO.setAula(programacionAcademica.getAula());
                    diaHorarioDTOS.add(diaHorarioDTO);
                }
                grupoDTO.setDiaHorarioDTOS(diaHorarioDTOS);
                grupoDTOS.add(grupoDTO);
            }
        }
        grupoDTOS.sort(Comparator.comparing(GrupoDTO::getSemestre)
                .thenComparing(GrupoDTO::getSiglas)
                .thenComparing(GrupoDTO::getDocente));
        return grupoDTOS;
    }

    public ReqRes createGrupo(newGrupoDTO registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            // Crear y asignar los datos básicos del grupo
            Grupo grupo = new Grupo();
            grupo.setName(registrationRequest.getNombre());

            // Obtener y asignar el docente, materia y facultad gestión
            OurUsers docente = usersRepo.findById(registrationRequest.getDocenteId()).orElseThrow(() -> new RuntimeException("User Not found"));
            Materia_Carrera materiaCarrera = materia_CarreraRepo.findById(registrationRequest.getMateria_carreraId()).orElseThrow(() -> new RuntimeException("Materia Carrera Not found"));
            Facultad_Gestion facultadGestion = facultad_GestionRepo.findById(registrationRequest.getFacultad_gestionId()).orElseThrow(() -> new RuntimeException("Facultad Gestion Not found"));

            grupo.setDocente(docente);
            grupo.setMateriaCarrera(materiaCarrera);
            grupo.setFacultadGestion(facultadGestion);

            // Guardar el grupo inicial
            Grupo grupoResult = grupoRepo.save(grupo);

            // Crear y asignar las programaciones académicas
            List<Programacion_Academica> programacionAcademicaList = new ArrayList<>();
            for (Dia_HorarioDTO diaHorarioDTO : registrationRequest.getDiaHorarioDTOS()) {
                Programacion_Academica programacionAcademica = new Programacion_Academica();
                programacionAcademica.setAula(diaHorarioDTO.getAula());

                // Obtener y asignar el módulo
                Modulo modulo = moduloRepo.findById(diaHorarioDTO.getModuloId()).orElseThrow(() -> new RuntimeException("Modulo Not found"));
                programacionAcademica.setModulo(modulo);

                // Obtener o crear el Dia_Horario
                Dia_Horario diaHorario = dia_HorarioRepo.findByDia_NameAndHorario_HoraInicioAndHorario_HoraFin(
                        diaHorarioDTO.getDia(),
                        diaHorarioDTO.getHoraInicio(),
                        diaHorarioDTO.getHoraFin()
                );

                if (diaHorario == null) {
                    Horario horario = new Horario();
                    horario.setHoraInicio(diaHorarioDTO.getHoraInicio());
                    horario.setHoraFin(diaHorarioDTO.getHoraFin());
                    Horario newHorario = horarioRepo.save(horario);

                    Dia dia = diaRepo.findByName(diaHorarioDTO.getDia());
                    Dia_Horario newDiaHorario = new Dia_Horario();
                    newDiaHorario.setHorario(newHorario);
                    newDiaHorario.setDia(dia);
                    diaHorario = dia_HorarioRepo.save(newDiaHorario);
                }

                programacionAcademica.setDiaHorario(diaHorario);
                programacionAcademica.setGrupo(grupoResult);

                // Guardar cada programación académica
                Programacion_Academica newProgramacion = programacion_AcademicaRepo.save(programacionAcademica);
                programacionAcademicaList.add(newProgramacion);
            }

            // Asignar la lista de programaciones académicas al grupo y guardar el grupo final
            grupoResult.setProgramacionAcademicas(programacionAcademicaList);
            Grupo finalGrupoResult = grupoRepo.save(grupoResult);

            // Verificar si se guardó correctamente
            if (finalGrupoResult.getId() > 0) {
                resp.setGrupo(finalGrupoResult);
                resp.setMessage("Grupo Saved Successfully");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }

        return resp;
    }



    public ReqRes getProgramacionesAcademicasByUsuario(int usuarioId) {
        ReqRes reqRes = new ReqRes();
        try {
            OurUsers usuario = usersRepo.findById(usuarioId).orElse(null);
            if (usuario == null) {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            List<Grupo> grupos = usuario.getGrupos();
            List<Programacion_Academica> programacionesAcademicas = grupos.stream()
                    .flatMap(grupo -> grupo.getProgramacionAcademicas().stream())
                    .collect(Collectors.toList());
            if (!programacionesAcademicas.isEmpty()) {
                reqRes.setProgramacionAcademicas(programacionesAcademicas);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No programaciones found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }

    }

    public ReqRes marcarAsistencia(int asistenciaId, LocalDateTime fecha, double latitud, double longitud) {
        ReqRes reqRes = new ReqRes();
        try {
            Asistencia asistencia = asistenciaRepo.getById(asistenciaId);
            LocalTime horaInicio = asistencia.getProgramacionAcademica().getDiaHorario().getHorario().getHoraInicio();
            LocalDateTime horaInicioLimite = fecha.withHour(horaInicio.getHour()).withMinute(horaInicio.getMinute()).plusMinutes(30);
            boolean puntual = fecha.isBefore(horaInicioLimite);

            Modulo modulo = asistencia.getProgramacionAcademica().getModulo();
            double distancia = calcularDistancia(latitud, longitud, modulo.getLatitud(), modulo.getLongitud());
            boolean dentroDelLimite = distancia <= 80.0;

            if (dentroDelLimite) {
                asistencia.setFecha(fecha);
                asistencia.setLatitud(latitud);
                asistencia.setLongitud(longitud);
                if(puntual) {
                    asistencia.setEstado("Presente");
                }else{
                    asistencia.setEstado("Atraso");
                }
                reqRes.setAsistencia(asistenciaRepo.save(asistencia));
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Fuera del limite de distancia (80 metros)");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    private static final int RADIO_TIERRA_METROS = 6371000;

    public double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
        double lat1Rad = Math.toRadians(latitud1);
        double lon1Rad = Math.toRadians(longitud1);
        double lat2Rad = Math.toRadians(latitud2);
        double lon2Rad = Math.toRadians(longitud2);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distanciaMetros = RADIO_TIERRA_METROS * c;

        return distanciaMetros;
    }

    public ReqRes solicitarLicencia(int docenteId, LocalDate fechaInicio, LocalDate fechaFin, String motivo) {
        ReqRes resp = new ReqRes();
        try {
            OurUsers ourUsers = usersRepo.findById(docenteId)
                    .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

            // Crear y guardar la nueva licencia
            Licencia licencia = new Licencia();
            licencia.setFechaInicio(fechaInicio);
            licencia.setFechaFin(fechaFin);
            licencia.setMotivo(motivo);
            licencia.setOurUsers(ourUsers);
            long daysBetween = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
            if (daysBetween <= 3) {
                licencia.setAprobado(true);
            } else {
                licencia.setAprobado(null);
            }
            Licencia licenciaResult = licenciaRepo.save(licencia);
            if(licenciaResult.getAprobado()) {
                List<Grupo> grupos = ourUsers.getGrupos();
                for (Grupo grupo : grupos) {
                    List<Programacion_Academica> programaciones = grupo.getProgramacionAcademicas();
                    for (Programacion_Academica programacion : programaciones) {
                        List<Asistencia> asistencias = programacion.getAsistencias();
                        for (Asistencia asistencia : asistencias) {
                            LocalDate fechaAsistencia = asistencia.getFecha().toLocalDate();
                            if ((fechaAsistencia.isEqual(fechaInicio) || fechaAsistencia.isAfter(fechaInicio)) &&
                                    (fechaAsistencia.isEqual(fechaFin) || fechaAsistencia.isBefore(fechaFin)) &&
                                    !"Presente".equals(asistencia.getEstado())) {
                                asistencia.setEstado("Licencia");
                                asistenciaRepo.save(asistencia);
                            }
                        }
                    }
                }
            }
            if (licenciaResult.getId() > 0) {
                resp.setLicencia(licenciaResult);
                resp.setMessage("Licencia Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes getAsistencias() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Asistencia> result = asistenciaRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setAsistenciaList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No grupos found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


}

