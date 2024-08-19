package com.perkpal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "activity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity extends BaseEntity{
    @Column(name = "activity_name",nullable = false,unique = true)
    private String activityName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",referencedColumnName = "id",nullable = false)
    Category categoryId;
    @Column(name = "weightage_per_hour",nullable = false)
    private int weightagePerHour;
    @OneToMany(mappedBy = "activityId",cascade = CascadeType.ALL,targetEntity = Participation.class)
    private Set<Participation> participationActivitySet = new HashSet<>();
}
