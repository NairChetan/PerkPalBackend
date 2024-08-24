package com.perkpal.repository;

import com.perkpal.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    List<Participation> findByApprovalStatus(String approvalStatus);
}

