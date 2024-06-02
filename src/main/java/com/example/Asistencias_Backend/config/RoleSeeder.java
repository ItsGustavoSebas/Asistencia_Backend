package com.example.Asistencias_Backend.config;

import com.example.Asistencias_Backend.entity.Permission;
import com.example.Asistencias_Backend.entity.Role;
import com.example.Asistencias_Backend.repository.PermissionRepo;
import com.example.Asistencias_Backend.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("RoleSeeder")
public class RoleSeeder implements CommandLineRunner, Ordered {

    @Override
    public int getOrder() {
        return 5; // Establece un orden posterior al de FacultadSeeder
    }


    @Autowired
    private RoleRepo rolRepository;

    @Autowired
    private PermissionRepo permisoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear permisos
        Permission permisoListarRoles = new Permission();
        permisoListarRoles.setName("Listar Roles");
        permisoRepository.save(permisoListarRoles);

        Permission permisoActualizarRoles = new Permission();
        permisoActualizarRoles.setName("Actualizar Roles");
        permisoRepository.save(permisoActualizarRoles);

        Permission permisoListarUsuarios = new Permission();
        permisoListarUsuarios.setName("Listar Usuarios");
        permisoRepository.save(permisoListarUsuarios);

        Permission permisoCrearUsuarios = new Permission();
        permisoCrearUsuarios.setName("Crear Usuarios");
        permisoRepository.save(permisoCrearUsuarios);

        Permission permisoEliminarUsuarios = new Permission();
        permisoEliminarUsuarios.setName("Eliminar Usuarios");
        permisoRepository.save(permisoEliminarUsuarios);

        Permission permisoActualizarUsuarios = new Permission();
        permisoActualizarUsuarios.setName("Actualizar Usuarios");
        permisoRepository.save(permisoActualizarUsuarios);

        Permission permisoListarModulos = new Permission();
        permisoListarModulos.setName("Listar Modulos");
        permisoRepository.save(permisoListarModulos);

        Permission permisoCrearModulos = new Permission();
        permisoCrearModulos.setName("Crear Modulos");
        permisoRepository.save(permisoCrearModulos);

        Permission permisoEliminarModulos = new Permission();
        permisoEliminarModulos.setName("Eliminar Modulos");
        permisoRepository.save(permisoEliminarModulos);

        Permission permisoActualizarModulos = new Permission();
        permisoActualizarModulos.setName("Actualizar Modulos");
        permisoRepository.save(permisoActualizarModulos);

        Permission permisoListarCarreras = new Permission();
        permisoListarCarreras.setName("Listar Carreras");
        permisoRepository.save(permisoListarCarreras);

        Permission permisoCrearCarreras = new Permission();
        permisoCrearCarreras.setName("Crear Carreras");
        permisoRepository.save(permisoCrearCarreras);

        Permission permisoEliminarCarreras = new Permission();
        permisoEliminarCarreras.setName("Eliminar Carreras");
        permisoRepository.save(permisoEliminarCarreras);

        Permission permisoActualizarCarreras = new Permission();
        permisoActualizarCarreras.setName("Actualizar Carreras");
        permisoRepository.save(permisoActualizarCarreras);

        Permission permisoListarMaterias = new Permission();
        permisoListarMaterias.setName("Listar Materias");
        permisoRepository.save(permisoListarMaterias);

        Permission permisoCrearMaterias = new Permission();
        permisoCrearMaterias.setName("Crear Materias");
        permisoRepository.save(permisoCrearMaterias);

        Permission permisoEliminarMaterias = new Permission();
        permisoEliminarMaterias.setName("Eliminar Materias");
        permisoRepository.save(permisoEliminarMaterias);

        Permission permisoActualizarMaterias = new Permission();
        permisoActualizarMaterias.setName("Actualizar Materias");
        permisoRepository.save(permisoActualizarMaterias);

        // Crear roles y asignar permisos
        Role rolAdmin = new Role();
        rolAdmin.setName("ADMIN");
        rolAdmin.setPermissions(Arrays.asList(permisoListarRoles, permisoActualizarRoles,
                permisoListarUsuarios, permisoCrearUsuarios, permisoActualizarUsuarios, permisoEliminarUsuarios,
                permisoListarModulos, permisoCrearModulos, permisoActualizarModulos, permisoEliminarModulos,
                permisoListarCarreras, permisoCrearCarreras, permisoActualizarCarreras, permisoEliminarCarreras,
                permisoListarMaterias, permisoCrearMaterias, permisoActualizarMaterias, permisoEliminarMaterias));
        rolRepository.save(rolAdmin);

        Role rolUsuario = new Role();
        rolUsuario.setName("USER");
        rolUsuario.setPermissions(Arrays.asList(permisoListarModulos, permisoListarCarreras, permisoListarMaterias));
        rolRepository.save(rolUsuario);

    }
}