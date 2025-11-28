package com.pedroluizforlan.pontodoc.service;

import java.util.List;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.dto.CollaboratorDTO;

public interface CollaboratorService {

    List<CollaboratorDTO> findAll();

    CollaboratorDTO findById(Long id);

    CollaboratorDTO create(Collaborator collaborator);

    CollaboratorDTO update(Long id, Collaborator collaborator);

    CollaboratorDTO delete(Long id);
}
