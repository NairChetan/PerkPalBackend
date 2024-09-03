package com.perkpal.repository;

import com.perkpal.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
    // JPA derived query method to find activities by category name
    List<Activity> findByCategoryIdCategoryName(String categoryName);
}


