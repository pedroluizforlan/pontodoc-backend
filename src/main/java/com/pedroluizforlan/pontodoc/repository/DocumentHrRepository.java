package com.pedroluizforlan.pontodoc.repository;

import com.pedroluizforlan.pontodoc.model.dto.DocumentHrDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pedroluizforlan.pontodoc.model.DocumentHR;

import java.util.List;

@Repository
public interface DocumentHrRepository extends JpaRepository<DocumentHR,Long>{

    @Query("""
    SELECT new com.pedroluizforlan.pontodoc.model.dto.DocumentHrDTO(
        hr.id, 
        hr.createdAt, 
        hr.documentHash, 
        hr.documentType, 
        hr.path, 
        hr.status, 
        hr.updatedAt, 
        hr.collaborator.id, 
        hr.collaborator.person.name, 
        hr.document_batch.id,
        hr.attribution
    )
    FROM DocumentHR hr
    WHERE hr.deletedAt IS NULL
    AND hr.status = 'WAITING'
""")
    List<DocumentHrDTO> findDocumentsToSignByCollaborator();
}
