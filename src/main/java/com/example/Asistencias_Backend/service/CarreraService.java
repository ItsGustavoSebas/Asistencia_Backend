
package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.AsistenciasDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.dto.UsuarioEstadoAsistenciasDTO;
import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarreraService {
    @Autowired
    private CarreraRepo carreraRepo;
    @Autowired
    private MateriaRepo materiaRepo;
    @Autowired
    private FacultadRepo facultadRepo;
    @Autowired
    private Materia_CarreraRepo materia_CarreraRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Transactional
    public ReqRes updateCarrera(int carrera_id, ReqRes reqRes) {
        ReqRes resp = new ReqRes();
        try {
            Optional<Carrera> carreraOptional = carreraRepo.findById(carrera_id);
            if (carreraOptional.isPresent()) {
                Carrera existingCarrera = carreraOptional.get();
                existingCarrera.setName(reqRes.getName());
                existingCarrera.setFacultad(facultadRepo.findById(reqRes.getFacultad().getId()).orElse(null));

                List<Materia_Carrera> existingMateriaCarreras = materia_CarreraRepo.findByCarrera(existingCarrera);
                Set<Integer> newMateriaIds = reqRes.getMaterias().stream()
                        .map(ReqRes.MateriaSemestre::getMateriaId)
                        .collect(Collectors.toSet());

                for (Materia_Carrera existingMateriaCarrera : existingMateriaCarreras) {
                    if (!newMateriaIds.contains(existingMateriaCarrera.getMateria().getId())) {
                        materia_CarreraRepo.delete(existingMateriaCarrera);
                    }
                }

                List<Materia_Carrera> newMateriaCarreras = new ArrayList<>();
                for (ReqRes.MateriaSemestre materiaSemestre : reqRes.getMaterias()) {
                    int materiaId = materiaSemestre.getMateriaId();
                    int semestre = materiaSemestre.getSemestre();

                    Materia materia = materiaRepo.findById(materiaId)
                            .orElseThrow(() -> new RuntimeException("Materia not found"));

                    Optional<Materia_Carrera> existingMateriaCarreraOpt = existingMateriaCarreras.stream()
                            .filter(mc -> mc.getMateria().getId() == materiaId)
                            .findFirst();

                    if (existingMateriaCarreraOpt.isPresent()) {
                        Materia_Carrera existingMateriaCarrera = existingMateriaCarreraOpt.get();
                        existingMateriaCarrera.setSemestre(semestre);
                        newMateriaCarreras.add(existingMateriaCarrera);
                    } else {
                        Materia_Carrera newMateriaCarrera = new Materia_Carrera();
                        newMateriaCarrera.setSemestre(semestre);
                        newMateriaCarrera.setMateria(materia);
                        newMateriaCarrera.setCarrera(existingCarrera);
                        newMateriaCarreras.add(newMateriaCarrera);
                    }
                }

                materia_CarreraRepo.saveAll(newMateriaCarreras);

                Carrera saveCarrera = carreraRepo.save(existingCarrera);
                resp.setCarrera(saveCarrera);
                resp.setStatusCode(200);
                resp.setMessage("Carrera updated successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Carrera not found for update");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred while updating carrera: " + e.getMessage());
        }
        return resp;
    }

    public Carrera getCarrera(int carreraId) {
        return carreraRepo.findById(carreraId)
                .orElseThrow(() -> new RuntimeException("Carrera not found"));
    }

    public ReqRes getCarrera() {
        ReqRes reqRes = new ReqRes();

        try {
            List<Carrera> result = carreraRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setCarreraList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No carreras found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes createCarrera(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();
        try {
            Carrera carrera = new Carrera();
            carrera.setName(registrationRequest.getName());
            carrera.setFacultad(facultadRepo.findById(registrationRequest.getFacultad().getId()).orElse(null));
            Carrera carreraResult = carreraRepo.save(carrera);
            if (carreraResult.getId() > 0) {
                List<ReqRes.MateriaSemestre> materiasSemestres = registrationRequest.getMaterias();
                List<Materia_Carrera> materiaCarreras = new ArrayList<>();
                for (ReqRes.MateriaSemestre materiaSemestre : materiasSemestres) {
                    Materia materia = materiaRepo.findById(materiaSemestre.getMateriaId()).orElseThrow(() -> new RuntimeException("Materia not found"));
                    Materia_Carrera materiaCarrera = new Materia_Carrera();
                    materiaCarrera.setSemestre(materiaSemestre.getSemestre());
                    materiaCarrera.setMateria(materia);
                    materiaCarrera.setCarrera(carreraResult);
                    materiaCarreras.add(materiaCarrera);
                }
                materia_CarreraRepo.saveAll(materiaCarreras);
                resp.setCarrera(carreraResult);
                resp.setMessage("Carrera Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteCarrera(int carreraId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Carrera> carreraOptional = carreraRepo.findById(carreraId);
            if (carreraOptional.isPresent()) {
                carreraRepo.deleteById(carreraId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Carrera deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Carrera not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting carrera: " + e.getMessage());
        }
        return reqRes;
    }

    public List<Materia_Carrera> getMaterias(int carreraId) {
        Carrera carrera = carreraRepo.findById(carreraId)
                .orElseThrow(() -> new RuntimeException("Carrera not found"));
        return materia_CarreraRepo.findByCarrera(carrera);
    }

    public ReqRes searchCarrerasByName(String name) {
        ReqRes reqRes = new ReqRes();
        try {
            List<Carrera> result = carreraRepo.findByNameContainingIgnoreCase(name);
            if (!result.isEmpty()) {
                reqRes.setCarreraList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No carreras found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasNameDate(int carreraId, String name, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findByRoles_NameAndNameContainingIgnoreCase("USER", name);
        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getId() == carreraId)
                    .collect(Collectors.toList());

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


    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasDate(int carreraId, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findByRoles_Name("USER");

        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getId() == carreraId)
                    .collect(Collectors.toList());
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

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistencias(int carreraId) {
        List<OurUsers> users = usersRepo.findByRoles_Name("USER");
        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getId() == carreraId)
                    .collect(Collectors.toList());
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

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasName(int carreraId, String name) {
        List<OurUsers> users = usersRepo.findByNameContainingIgnoreCase(name);

        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getId() == carreraId)
                    .collect(Collectors.toList());
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

    public List<AsistenciasDTO> getAsistenciasByUserIdWeb(int carreraId) {
        List<OurUsers> users = usersRepo.findAll();
        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getId() == carreraId)
                    .collect(Collectors.toList());
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
        }
        asistenciasDTO.sort(Comparator.comparing(AsistenciasDTO::getFecha));
        return asistenciasDTO;
    }

    public List<AsistenciasDTO> getAsistenciasByUserIdDate(int carreraId, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findAll();
        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getId() == carreraId)
                    .collect(Collectors.toList());
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
        }
        asistenciasDTO.sort(Comparator.comparing(AsistenciasDTO::getFecha));
        return asistenciasDTO;
    }

    public List<AsistenciasDTO> getAsistenciasByUserIdEstadoDate(int carreraId, String estado, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findAll();
        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getId() == carreraId)
                    .collect(Collectors.toList());
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

        }
        asistenciasDTO.sort(Comparator.comparing(AsistenciasDTO::getFecha));
        return asistenciasDTO;
    }

    public List<AsistenciasDTO> getAsistenciasByUserIdEstado(int carreraId, String estado) {
        List<OurUsers> users = usersRepo.findAll();
        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getId() == carreraId)
                    .collect(Collectors.toList());
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

        }
        asistenciasDTO.sort(Comparator.comparing(AsistenciasDTO::getFecha));
        return asistenciasDTO;
    }
}
