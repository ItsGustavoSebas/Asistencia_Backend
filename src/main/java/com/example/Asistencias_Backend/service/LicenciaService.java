package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.dto.LicenciaDTO;
import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.*;
import com.example.Asistencias_Backend.repository.LicenciaRepo;
import com.example.Asistencias_Backend.repository.UsersRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class LicenciaService {
    @Autowired
    private LicenciaRepo licenciaRepository;

    @Autowired
    private UsersRepo usersRepo;

    public List<LicenciaDTO> getLicencias() {
        List<OurUsers> users = usersRepo.findAll();
        List<LicenciaDTO> licenciasDTO = new ArrayList<>();
        for (OurUsers user : users) {
            List<Licencia> licencias = user.getLicencias();
            for (Licencia licencia : licencias) {
                LicenciaDTO licenciaDTO = new LicenciaDTO();
                licenciaDTO.setNombre(licencia.getOurUsers().getName());
                licenciaDTO.setId(licencia.getId());
                licenciaDTO.setMotivo(licencia.getMotivo());
                licenciaDTO.setAprobado(licencia.getAprobado());
                licenciaDTO.setFechaInicio(licencia.getFechaInicio());
                licenciaDTO.setFechaFin(licencia.getFechaFin());
                licenciasDTO.add(licenciaDTO);
            }
        }
        licenciasDTO.sort(Comparator.comparing(LicenciaDTO::getFechaInicio));
        return licenciasDTO;
    }

    public List<LicenciaDTO> getLicenciasByEstado(String estado) {
        List<OurUsers> users = usersRepo.findAll();
        List<LicenciaDTO> licenciasDTO = new ArrayList<>();

        Boolean aprobado = null;
        if ("aprobado".equalsIgnoreCase(estado)) {
            aprobado = true;
        } else if ("rechazado".equalsIgnoreCase(estado)) {
            aprobado = false;
        } else if ("pendiente".equalsIgnoreCase(estado)) {
            aprobado = null;
        } else {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }

        for (OurUsers user : users) {
            List<Licencia> licencias = user.getLicencias();
            for (Licencia licencia : licencias) {
                if (estado.equalsIgnoreCase("pendiente") && licencia.getAprobado() == null
                        || estado.equalsIgnoreCase("aprobado") && Boolean.TRUE.equals(licencia.getAprobado())
                        || estado.equalsIgnoreCase("rechazado") && Boolean.FALSE.equals(licencia.getAprobado())) {

                    LicenciaDTO licenciaDTO = new LicenciaDTO();
                    licenciaDTO.setNombre(licencia.getOurUsers().getName());
                    licenciaDTO.setId(licencia.getId());
                    licenciaDTO.setMotivo(licencia.getMotivo());
                    licenciaDTO.setAprobado(licencia.getAprobado());
                    licenciaDTO.setFechaInicio(licencia.getFechaInicio());
                    licenciaDTO.setFechaFin(licencia.getFechaFin());
                    licenciasDTO.add(licenciaDTO);
                }
            }
        }

        licenciasDTO.sort(Comparator.comparing(LicenciaDTO::getFechaInicio));
        return licenciasDTO;
    }

    public List<LicenciaDTO> getLicenciasDate(LocalDate startDate, LocalDate endDate) {
        List<OurUsers> users = usersRepo.findAll();
        List<LicenciaDTO> licenciasDTO = new ArrayList<>();

        for (OurUsers user : users) {
            List<Licencia> licencias = user.getLicencias();
            for (Licencia licencia : licencias) {
                LocalDate fechaInicio = licencia.getFechaInicio();
                LocalDate fechaFin = licencia.getFechaFin();

                if ((fechaInicio.isEqual(startDate) || fechaInicio.isAfter(startDate)) &&
                        (fechaInicio.isEqual(endDate) || fechaInicio.isBefore(endDate)) ||
                        (fechaFin.isEqual(startDate) || fechaFin.isAfter(startDate)) &&
                                (fechaFin.isEqual(endDate) || fechaFin.isBefore(endDate))) {

                    LicenciaDTO licenciaDTO = new LicenciaDTO();
                    licenciaDTO.setNombre(user.getName());
                    licenciaDTO.setId(licencia.getId());
                    licenciaDTO.setMotivo(licencia.getMotivo());
                    licenciaDTO.setAprobado(licencia.getAprobado());
                    licenciaDTO.setFechaInicio(fechaInicio);
                    licenciaDTO.setFechaFin(fechaFin);
                    licenciasDTO.add(licenciaDTO);
                }
            }
        }

        licenciasDTO.sort(Comparator.comparing(LicenciaDTO::getFechaInicio));
        return licenciasDTO;
    }



    public List<LicenciaDTO> getLicenciasByEstadoDate(String estado, LocalDate  startDate, LocalDate  endDate) {
        List<OurUsers> users = usersRepo.findAll();
        List<LicenciaDTO> licenciasDTO = new ArrayList<>();

        Boolean aprobado = null;
        if ("aprobado".equalsIgnoreCase(estado)) {
            aprobado = true;
        } else if ("rechazado".equalsIgnoreCase(estado)) {
            aprobado = false;
        } else if ("pendiente".equalsIgnoreCase(estado)) {
            aprobado = null;
        } else {
            throw new IllegalArgumentException("Estado no válido: " + estado);
        }

        for (OurUsers user : users) {
            List<Licencia> licencias = user.getLicencias();
            for (Licencia licencia : licencias) {
                LocalDate fechaInicio = licencia.getFechaInicio();
                LocalDate fechaFin = licencia.getFechaFin();
                if ((estado.equalsIgnoreCase("pendiente") && licencia.getAprobado() == null
                        || estado.equalsIgnoreCase("aprobado") && Boolean.TRUE.equals(licencia.getAprobado())
                        || estado.equalsIgnoreCase("rechazado") && Boolean.FALSE.equals(licencia.getAprobado()))
                        && ((fechaInicio.isEqual(startDate) || fechaInicio.isAfter(startDate)) &&
                        (fechaInicio.isEqual(endDate) || fechaInicio.isBefore(endDate)) ||
                        (fechaFin.isEqual(startDate) || fechaFin.isAfter(startDate)) &&
                                (fechaFin.isEqual(endDate) || fechaFin.isBefore(endDate)))) {
                    LicenciaDTO licenciaDTO = new LicenciaDTO();
                    licenciaDTO.setNombre(licencia.getOurUsers().getName());
                    licenciaDTO.setId(licencia.getId());
                    licenciaDTO.setMotivo(licencia.getMotivo());
                    licenciaDTO.setAprobado(licencia.getAprobado());
                    licenciaDTO.setFechaInicio(licencia.getFechaInicio());
                    licenciaDTO.setFechaFin(licencia.getFechaFin());
                    licenciasDTO.add(licenciaDTO);
                }
            }
        }

        licenciasDTO.sort(Comparator.comparing(LicenciaDTO::getFechaInicio));
        return licenciasDTO;
    }


    @Transactional
    public ReqRes aprobar(int licencia_id) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Licencia> licenciaOptional = licenciaRepository.findById(licencia_id);
            if (licenciaOptional.isPresent()) {
                Licencia existingLicencia = licenciaOptional.get();
                existingLicencia.setAprobado(true);
                Licencia saveLicencia = licenciaRepository.save(existingLicencia);
                reqRes.setLicencia(saveLicencia);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Licencia updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Licencia not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating licencia: " + e.getMessage());
        }
        return reqRes;
    }

    @Transactional
    public ReqRes rechazar(int licencia_id) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Licencia> licenciaOptional = licenciaRepository.findById(licencia_id);
            if (licenciaOptional.isPresent()) {
                Licencia existingLicencia = licenciaOptional.get();
                existingLicencia.setAprobado(false);
                Licencia saveLicencia = licenciaRepository.save(existingLicencia);
                reqRes.setLicencia(saveLicencia);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Licencia updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Licencia not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating licencia: " + e.getMessage());
        }
        return reqRes;
    }
}
