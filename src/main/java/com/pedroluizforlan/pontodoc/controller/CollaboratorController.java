package com.pedroluizforlan.pontodoc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.service.CollaboratorService;

@RestController
@RequestMapping("/api/collaborator")
public class CollaboratorController {
    
    private final CollaboratorService collaboratorService;

    public CollaboratorController(CollaboratorService collaboratorService){
        this.collaboratorService = collaboratorService;
    }

    @GetMapping
    public ResponseEntity<List<Collaborator>> getAllCollaborator(){
        List<Collaborator> collaborators = collaboratorService.findAll();
        return ResponseEntity.ok(collaborators);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Collaborator> getCollaborator(@PathVariable Long id){
        Collaborator collaborator = collaboratorService.findById(id);
        return ResponseEntity.ok(collaborator);
    }

    @PostMapping
    public ResponseEntity<Collaborator> createCollaborator(@RequestBody Collaborator collaborator){
        return ResponseEntity.ok(collaboratorService.create(collaborator));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Collaborator> updateCollaborator(@PathVariable Long id, @RequestBody Collaborator collaborator){
        return ResponseEntity.ok(collaboratorService.update(id, collaborator));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Collaborator> deleteCollaborator(@PathVariable Long id){
        return ResponseEntity.ok(collaboratorService.delete(id));
    }

}
