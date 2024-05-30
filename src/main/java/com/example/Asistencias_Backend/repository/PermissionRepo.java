package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission, Integer> {
        Permission findById(int id);
}
