package com.pedroluizforlan.pontodoc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.dto.CollaboratorDTO;
import com.pedroluizforlan.pontodoc.service.CollaboratorService;

@RestController
@RequestMapping("/api/collaborator")
public class CollaboratorController {
    
    private final CollaboratorService collaboratorService;

    public CollaboratorController(CollaboratorService collaboratorService){
        this.collaboratorService = collaboratorService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<CollaboratorDTO>> getAllCollaborator(){
        List<CollaboratorDTO> collaboratorDTOs = collaboratorService.findAll();
        return ResponseEntity.ok(collaboratorDTOs);
    }
    
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<CollaboratorDTO> getCollaborator(@PathVariable Long id){
        CollaboratorDTO collaboratorDTO = collaboratorService.findById(id);
        return ResponseEntity.ok(collaboratorDTO);
    }

    @PostMapping
    public ResponseEntity<CollaboratorDTO> createCollaborator(@RequestBody Collaborator collaborator){
        return ResponseEntity.ok(collaboratorService.create(collaborator));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<CollaboratorDTO> updateCollaborator(@PathVariable Long id, @RequestBody Collaborator collaborator){
        return ResponseEntity.ok(collaboratorService.update(id, collaborator));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<CollaboratorDTO> deleteCollaborator(@PathVariable Long id){
        return ResponseEntity.ok(collaboratorService.delete(id));
    }

}
