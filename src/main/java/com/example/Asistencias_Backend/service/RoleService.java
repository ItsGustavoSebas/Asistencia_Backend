package com.example.Asistencias_Backend.service;

import com.example.Asistencias_Backend.entity.Permission;
import com.example.Asistencias_Backend.entity.Role;
import com.example.Asistencias_Backend.repository.PermissionRepo;
import com.example.Asistencias_Backend.repository.RoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PermissionRepo permissionRepository;

    @Transactional
    public void updateRolePermissions(int roleId, List<Integer> permissionIds) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));

        // Eliminar todos los permisos existentes del rol
        role.getPermissions().clear();

        // Añadir los nuevos permisos a partir de los IDs recibidos
        List<Permission> permissions = permissionIds.stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + permissionId)))
                .collect(Collectors.toList());
        role.getPermissions().addAll(permissions);

        roleRepository.save(role);
    }

    public Role getRole(int roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

}

