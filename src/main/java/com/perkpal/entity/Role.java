package com.perkpal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="role")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name="created_date",updatable = false,nullable = false)
    private Timestamp createdDate;
    @UpdateTimestamp
    @Column(name="updated_date")
    private Timestamp updatedDate;
    @Column(name = "role_name",nullable = false,unique = true)
    private String roleName;
    @OneToMany(mappedBy = "roleId",cascade = CascadeType.ALL,targetEntity = Employee.class)
    private Set<Employee> employeeSet = new HashSet<>();
}
