package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.repository.CollaboratorRepository;
import com.pedroluizforlan.pontodoc.service.CollaboratorService;

@Service
public class CollaboratorServiceImp implements CollaboratorService{

    private final CollaboratorRepository collaboratorRepository;

    public CollaboratorServiceImp(CollaboratorRepository collaboratorRepository){
        this.collaboratorRepository = collaboratorRepository;
    }

    @Override
    public List<Collaborator> findAll() {
        var collaborators = collaboratorRepository.findAll();
        return collaborators;
    }

    @Override
    public Collaborator findById(Long id) {
        return collaboratorRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Collaborator not found with id: " + id));
    }

    @Override
    public Collaborator create(Collaborator entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Collaborator update(Long id, Collaborator entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Collaborator delete(Long id) {
        Collaborator collaborator = findById(id);
        collaborator.setDeleatedAt(LocalDateTime.now());
        return collaboratorRepository.save(collaborator);
    }
    
}
