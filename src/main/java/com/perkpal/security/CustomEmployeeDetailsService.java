package com.perkpal.security;

import com.perkpal.entity.Employee;
import com.perkpal.repository.EmployeeRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomEmployeeDetailsService implements UserDetailsService {

    private EmployeeRepository employeeRepository;

    public CustomEmployeeDetailsService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Employee not found with this email: "+email));
        // Map the single role to authorities
        Set<GrantedAuthority> authorities = Set.of(
                new SimpleGrantedAuthority(employee.getRoleId().getRoleName()) // Converts the role to a granted authority
        );
        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(),
                employee.getEmail(),
                authorities);
    }
}
