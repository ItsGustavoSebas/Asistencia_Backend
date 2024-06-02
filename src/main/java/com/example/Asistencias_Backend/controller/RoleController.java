package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.dto.ReqRes;
import com.example.Asistencias_Backend.entity.Role;
import com.example.Asistencias_Backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<ReqRes> updateRolePermissions(@PathVariable int roleId, @RequestBody List<Integer> permissionIds) {
        return ResponseEntity.ok(roleService.updateRolePermissions(roleId, permissionIds));

    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRole(@PathVariable int roleId) {
        Role role = roleService.getRole(roleId);
        return ResponseEntity.ok(role);
    }

    @GetMapping("")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getRoles();
        return ResponseEntity.ok(roles);
    }
}
