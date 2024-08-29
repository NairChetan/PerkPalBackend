package com.perkpal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "participation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participation extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("participation")
    @JoinColumn(name = "emp_id", referencedColumnName = "id", nullable = false)
    Employee employee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("participationActivitySet")
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
    Activity activityId;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "remarks")
    private String remarks;
    @CreationTimestamp
    @Column(name = "participation_date", nullable = false)
    private Timestamp participationDate;

    @Column(name = "approval_date")
    private Timestamp approvalDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("participationApproved")
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    Employee approvedBy;
    @Column(name = "approval_status", nullable = false)
    private String approvalStatus = "pending";
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "proof_url")
    private String proofUrl;
}