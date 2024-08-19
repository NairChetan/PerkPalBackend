package com.perkpal.repository;

import com.perkpal.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
}
