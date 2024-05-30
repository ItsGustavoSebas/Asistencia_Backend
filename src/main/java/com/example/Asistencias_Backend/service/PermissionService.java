package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.entity.Cargo;
import com.example.Asistencias_Backend.entity.Permission;
import com.example.Asistencias_Backend.repository.CargoRepo;
import com.example.Asistencias_Backend.repository.PermissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepo permissionRepo;

    public List<Permission> getPermissions() {
        return permissionRepo.findAll();
    }

}

