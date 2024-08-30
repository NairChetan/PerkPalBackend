package com.perkpal.service.impl;

import com.perkpal.dto.LoginDto;
import com.perkpal.entity.Employee;
import com.perkpal.repository.EmployeeRepository;
import com.perkpal.security.JwtTokenProvider;
import com.perkpal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private
    JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        // Extract email from LoginDto
        String emailFromSSO = loginDto.getEmail();

        // Load user by email
        Employee employee = employeeRepository.findByEmail(emailFromSSO)
                .orElseThrow(() -> new UsernameNotFoundException("Employee not found with email: " + emailFromSSO));

        // Authenticate and set the SecurityContext if needed
        Authentication authentication = new UsernamePasswordAuthenticationToken(employee.getEmail(), null, employee.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate a token for your application
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public Optional<Employee> loadEmployeeByEmail(String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        return employee;
    }


}
