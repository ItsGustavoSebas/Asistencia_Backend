package com.example.Asistencias_Backend.repository;

import com.example.Asistencias_Backend.entity.OurUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OurUserRepo extends JpaRepository<OurUsers, Integer> {

    Optional<OurUsers> findByEmail(String email);
}
