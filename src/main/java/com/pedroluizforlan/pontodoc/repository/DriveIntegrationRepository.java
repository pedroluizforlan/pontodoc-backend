package com.pedroluizforlan.pontodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroluizforlan.pontodoc.model.DriveIntegration;

@Repository
public interface DriveIntegrationRepository extends JpaRepository<DriveIntegration, Long> {
    
}
