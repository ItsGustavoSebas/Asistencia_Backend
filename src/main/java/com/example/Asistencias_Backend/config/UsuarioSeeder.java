package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Cargo;
import com.example.Asistencias_Backend.entity.OurUsers;
import com.example.Asistencias_Backend.entity.Role;
import com.example.Asistencias_Backend.repository.CargoRepo;
import com.example.Asistencias_Backend.repository.RoleRepo;
import com.example.Asistencias_Backend.repository.UsersRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component("UsuarioSeeder")
@DependsOn({"RoleSeeder", "CargoSeeder"})
public class UsuarioSeeder implements CommandLineRunner, Ordered {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int getOrder() {
        return 6; // Establece un orden posterior al de FacultadSeeder
    }

    @Autowired
    private RoleRepo rolRepository;

    @Autowired
    private UsersRepo usuarioRepository;

    @Autowired
    private CargoRepo cargoRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role role1 = rolRepository.findById(1).orElseThrow(() -> new RuntimeException("Role not found"));
        Role role2 = rolRepository.findById(2).orElseThrow(() -> new RuntimeException("Role not found"));

        Cargo cargo1 = cargoRepo.findById(1).orElseThrow(() -> new RuntimeException("Cargo not found"));
        Cargo cargo2 = cargoRepo.findById(2).orElseThrow(() -> new RuntimeException("Cargo not found"));
        Cargo cargo3 = cargoRepo.findById(3).orElseThrow(() -> new RuntimeException("Cargo not found"));

        OurUsers usuario1 = new OurUsers();
        usuario1.setName("Rector");
        usuario1.setEmail("rector@gmail.com");
        usuario1.setPassword(passwordEncoder.encode("12345678"));
        usuario1.setCargo(cargo1);
        usuario1.setRoles(Arrays.asList(role1, role2));
        usuarioRepository.save(usuario1);

        OurUsers usuario2 = new OurUsers();
        usuario2.setName("Vice Rector");
        usuario2.setEmail("vicerector@gmail.com");
        usuario2.setPassword(passwordEncoder.encode("12345678"));
        usuario2.setCargo(cargo2);
        usuario2.setRoles(Arrays.asList(role1, role2));
        usuarioRepository.save(usuario2);

        OurUsers usuario3 = new OurUsers();
        usuario3.setName("Docente1");
        usuario3.setEmail("docente1@gmail.com");
        usuario3.setPassword(passwordEncoder.encode("12345678"));
        usuario3.setCargo(cargo3);
        usuario3.setRoles(Arrays.asList(role2));
        usuarioRepository.save(usuario3);

        OurUsers usuario4 = new OurUsers();
        usuario4.setName("Docente2");
        usuario4.setEmail("docente2@gmail.com");
        usuario4.setPassword(passwordEncoder.encode("12345678"));
        usuario4.setCargo(cargo3);
        usuario4.setRoles(Arrays.asList(role2));
        usuarioRepository.save(usuario4);
    }
}
