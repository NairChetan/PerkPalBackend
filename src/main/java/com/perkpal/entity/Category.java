package com.perkpal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity{
    @Column(name = "category_name",nullable = false,unique = true)
    private String categoryName;
    @OneToMany(mappedBy = "categoryId",cascade = CascadeType.ALL,targetEntity = Activity.class)
    private Set<Activity> activities = new HashSet<>();
}
