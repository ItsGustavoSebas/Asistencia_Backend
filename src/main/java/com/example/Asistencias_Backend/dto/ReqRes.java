package com.example.Asistencias_Backend.dto;

import com.example.Asistencias_Backend.entity.OurUsers;
import com.example.Asistencias_Backend.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String city;
    private List<String> roles;
    private List<String> permissions;
    private String email;
    private String password;
<<<<<<< HEAD
=======
    private List<Product> products;
    private List<OurUsers> ourUsersList;
>>>>>>> origin/main
    private OurUsers ourUsers;
    private List<OurUsers> ourUsersList;
    private List<Role> roleList;
    private Integer cargoId;
    private Role role;
}