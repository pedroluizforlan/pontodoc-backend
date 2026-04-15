package com.pedroluizforlan.pontodoc.repository;

import java.util.List;

import com.pedroluizforlan.pontodoc.model.dto.CollaboratorAndDocumentsToSignDTO;
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

    @Query("""
        SELECT new com.pedroluizforlan.pontodoc.model.dto.CollaboratorAndDocumentsToSignDTO(
            c,
            dh
        )
        FROM DocumentHR dh
        JOIN dh.collaborator c
        JOIN c.person p
        JOIN c.user u
        WHERE dh.status = com.pedroluizforlan.pontodoc.model.DocumentHR$Status.WAITING
        ORDER BY p.name
    """)
    List<CollaboratorAndDocumentsToSignDTO> findCollaboratorsToSignDoc();
}