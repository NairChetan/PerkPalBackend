package com.perkpal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name="created_by",updatable = false,nullable = false)
    private Long createdBy;
    @Column(name="updated_by")
    private Long updatedBy;
    @CreationTimestamp
    @Column(name="created_date",updatable = false,nullable = false)
    private Timestamp createdDate;
    @UpdateTimestamp
    @Column(name="updated_date")
    private Timestamp updatedDate;
    @Column(name="description")
    private String description;
}
