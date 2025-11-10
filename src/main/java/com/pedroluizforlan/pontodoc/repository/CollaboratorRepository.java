package com.pedroluizforlan.pontodoc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pedroluizforlan.pontodoc.model.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long>{
    
}
