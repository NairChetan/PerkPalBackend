package com.perkpal.repository;

import com.perkpal.entity.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Page<Participation> findByApprovalStatus(String approvalStatus, Pageable pageable);
}

