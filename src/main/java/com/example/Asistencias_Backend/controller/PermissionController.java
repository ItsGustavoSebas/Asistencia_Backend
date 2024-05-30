package com.example.Asistencias_Backend.controller;

import com.example.Asistencias_Backend.entity.Permission;
import com.example.Asistencias_Backend.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public ResponseEntity<List<Permission>> getPermissions() {
        List<Permission> permissions = permissionService.getPermissions();
        return ResponseEntity.ok(permissions);
    }
}
