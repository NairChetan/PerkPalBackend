package com.perkpal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "club")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Club extends BaseEntity {
    @Column(name = "club_name", nullable = false, unique = true)
    private String clubName;
    @Column(name = "club_description")
    private String clubDescription;
    @Column(name = "initial_threshold", nullable = false)
    private Long initialThreshold;
    @Column(name = "final_threshold", nullable = false)
    private Long FinalThreshold;
    @OneToMany(mappedBy = "clubId", cascade = CascadeType.ALL, targetEntity = Employee.class)
    private Set<Employee> employeeClub = new HashSet<>();

}
