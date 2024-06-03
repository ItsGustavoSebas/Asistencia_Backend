package com.example.Asistencias_Backend.repository;


import com.example.Asistencias_Backend.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);
    List<OurUsers> findByRoles_Name(String roleName);
    List<OurUsers> findByNameContainingIgnoreCase(String name);
    List<OurUsers> findByRoles_NameAndNameContainingIgnoreCase(String roleName, String name);
}
