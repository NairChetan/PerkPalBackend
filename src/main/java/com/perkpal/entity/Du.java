package com.perkpal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="du")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Du {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false)
    private String departmentName;

    @OneToMany(mappedBy = "duId", cascade = CascadeType.ALL, targetEntity = Employee.class)
    private Set<Employee> employees = new HashSet<>();
}
