package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.AsistenciasDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.dto.UsuarioEstadoAsistenciasDTO;
import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.FacultadRepo;
import com.example.Asistencias_Backend.repository.UsersRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultadService {


    private final FacultadRepo facultadRepo;

    @Autowired
    private UsersRepo usersRepo;

    public FacultadService(FacultadRepo facultadRepo) {
        this.facultadRepo = facultadRepo;
    }

    public List<Facultad> getAllFacultades() {
        return facultadRepo.findAll();
    }

    @Transactional
    public ReqRes updateFacultad(int facultad_id, Facultad facultad) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Facultad> facultadOptional = facultadRepo.findById(facultad_id);
            if (facultadOptional.isPresent()) {
                Facultad existingFacultad = facultadOptional.get();
                existingFacultad.setName(facultad.getName());
                Facultad saveFacultad = facultadRepo.save(existingFacultad);
                reqRes.setFacultad(saveFacultad);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Facultad updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Facultad not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating facultad: " + e.getMessage());
        }
        return reqRes;
    }

    public Facultad getFacultad(int facultadId) {
        return facultadRepo.findById(facultadId)
                .orElseThrow(() -> new RuntimeException("Facultad not found"));
    }

    public ReqRes getFacultad() {
        ReqRes reqRes = new ReqRes();
        try {
            List<Facultad> result = facultadRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setFacultadList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No facultades found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes createFacultad(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            Facultad facultad = new Facultad();
            facultad.setName(registrationRequest.getName());
            Facultad facultadResult = facultadRepo.save(facultad);

            if (facultadResult.getId() > 0) {
                resp.setFacultad(facultadResult);
                resp.setMessage("Facultad Saved Successfully");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes deleteFacultad(int facultadId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Facultad> facultadOptional = facultadRepo.findById(facultadId);
            if (facultadOptional.isPresent()) {
                facultadRepo.deleteById(facultadId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Facultad deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Facultad not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting facultad: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes searchFacultadsByName(String name) {
        ReqRes reqRes = new ReqRes();

        try {
            List<Facultad> result = facultadRepo.findByNameContainingIgnoreCase(name);
            if (!result.isEmpty()) {
                reqRes.setFacultadList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No facultades found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasNameDate(int facultadId, String name, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findByRoles_NameAndNameContainingIgnoreCase("USER", name);
        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getFacultad().getId() == facultadId)
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


    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasDate(int facultadId, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findByRoles_Name("USER");

        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getFacultad().getId() == facultadId)
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

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistencias(int facultadId) {
        List<OurUsers> users = usersRepo.findByRoles_Name("USER");
        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getFacultad().getId() == facultadId)
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

    public List<UsuarioEstadoAsistenciasDTO> getAllUsersEstadoAsistenciasName(int facultadId, String name) {
        List<OurUsers> users = usersRepo.findByNameContainingIgnoreCase(name);

        List<UsuarioEstadoAsistenciasDTO> usuariosEstadoAsistenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getFacultad().getId() == facultadId)
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

    public List<AsistenciasDTO> getAsistenciasByUserIdWeb(int facultadId) {
        List<OurUsers> users = usersRepo.findAll();
        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getFacultad().getId() == facultadId)
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

    public List<AsistenciasDTO> getAsistenciasByUserIdDate(int facultadId, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findAll();
        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getFacultad().getId() == facultadId)
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

    public List<AsistenciasDTO> getAsistenciasByUserIdEstadoDate(int facultadId, String estado, Date startDate, Date endDate) {
        List<OurUsers> users = usersRepo.findAll();
        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getFacultad().getId() == facultadId)
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

    public List<AsistenciasDTO> getAsistenciasByUserIdEstado(int facultadId, String estado) {
        List<OurUsers> users = usersRepo.findAll();
        List<AsistenciasDTO> asistenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Grupo> grupos = user.getGrupos().stream()
                    .filter(grupo -> grupo.getMateriaCarrera().getCarrera().getFacultad().getId() == facultadId)
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
