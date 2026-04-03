package com.pedroluizforlan.pontodoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.pedroluizforlan.pontodoc.model.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long>{

    @Query(value = """
        SELECT p.name 
        FROM collaborators c
        JOIN persons p ON p.id = c.person_id
        WHERE c.is_active <> false
    """, nativeQuery = true)
    List<String> gellAllNames();
}
