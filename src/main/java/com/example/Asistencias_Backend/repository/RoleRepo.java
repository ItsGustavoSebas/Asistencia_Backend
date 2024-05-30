package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
