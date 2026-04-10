package com.pedroluizforlan.pontodoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.pedroluizforlan.pontodoc.model.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long>{

    @Query(value = """
        SELECT c
        FROM Collaborator c
        WHERE c.isActive <> false
    """)
    List<Collaborator> gellAllNames();
}
