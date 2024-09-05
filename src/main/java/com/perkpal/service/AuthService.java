package com.perkpal.service;

import com.perkpal.dto.LoginDto;
import com.perkpal.entity.Employee;

import java.util.Optional;

public interface AuthService {
    String login(LoginDto loginDto);
     Optional<Employee> loadEmployeeByEmail(String email);
}
