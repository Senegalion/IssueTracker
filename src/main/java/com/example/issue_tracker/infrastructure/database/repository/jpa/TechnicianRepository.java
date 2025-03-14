package com.example.issue_tracker.infrastructure.database.repository.jpa;

import com.example.issue_tracker.infrastructure.database.entity.TechnicianEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicianRepository extends JpaRepository<TechnicianEntity, Long> {
}
