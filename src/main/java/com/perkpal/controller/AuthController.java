package com.perkpal.controller;

import com.perkpal.dto.JWTAuthResponse;
import com.perkpal.dto.LoginDto;
import com.perkpal.entity.Employee;
import com.perkpal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        Employee employee = authService.loadEmployeeByEmail(loginDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: "+loginDto.getEmail()));
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setClubName(employee.getClubId().getClubName());
        jwtAuthResponse.setDuName(employee.getDuId().getDepartmentName());
        jwtAuthResponse.setEmployeeId(employee.getId());
        jwtAuthResponse.setFirstName(employee.getFirstName());
        jwtAuthResponse.setLastName(employee.getLastName());
        jwtAuthResponse.setPhotoUrl(employee.getPhotoUrl());
        jwtAuthResponse.setRoleName(employee.getRoleId().getRoleName());
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
