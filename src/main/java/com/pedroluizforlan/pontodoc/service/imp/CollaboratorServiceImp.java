package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.pedroluizforlan.pontodoc.model.dto.CollaboratorAndDocumentsToSignDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.DriveIntegration;
import com.pedroluizforlan.pontodoc.model.EmailLog;
import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.model.User;
import com.pedroluizforlan.pontodoc.model.dto.CollaboratorDTO;
import com.pedroluizforlan.pontodoc.model.mapper.CollaboratorMapper;
import com.pedroluizforlan.pontodoc.repository.CollaboratorRepository;
import com.pedroluizforlan.pontodoc.service.CollaboratorService;
import com.pedroluizforlan.pontodoc.service.DriveIntegrationService;
import com.pedroluizforlan.pontodoc.service.PersonService;
import com.pedroluizforlan.pontodoc.service.UserService;
import com.pedroluizforlan.pontodoc.service.integrations.GoogleDriveService;

//@TODO VALIDAR E OTIMIZAR
@Slf4j
@RequiredArgsConstructor
@Service
public class CollaboratorServiceImp implements CollaboratorService{

    private final CollaboratorRepository collaboratorRepository;
    private final PersonService personService;
    private final UserService userService;
    private final EmailLogServiceImp emailLogServiceImp;
    private final DriveIntegrationService driveIntegrationService;
    private final GoogleDriveService googleDriveService;
    private final CollaboratorMapper mapper;


    @Override
    public List<CollaboratorDTO> findAll() {
        var collaborators = collaboratorRepository.findAll();
        var collaboratorsReturn = collaborators
                                    .stream()
                                    .filter(c -> c.getUser().getUseType() != User.UserType.MANAGER && c.getUser().getDeletedAt() == null)
                                    .map(mapper::toDTO)
                                    .toList();
        return collaboratorsReturn;
    }

    @Override
    public CollaboratorDTO findById(Long id) {
        Collaborator collaborator = collaboratorRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Collaborator not found with id: " + id));
        return mapper.toDTO(collaborator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CollaboratorDTO create(Collaborator collaborator) {
        try{
            Person cPerson = personService.create(collaborator.getPerson());

            var password = collaborator.getUser().getPassword();
            User cUser = userService.create(collaborator.getUser());

            collaborator.setPerson(cPerson);
            collaborator.setUser(cUser);

            collaborator.setCreatedAt(LocalDateTime.now());

            var newCollaborator = collaboratorRepository.save(collaborator);

            var folderId = googleDriveService.createFolderForCollaborator(collaborator.getPerson().getName());
            var driveIntegration = this.createFolder(folderId, newCollaborator);

            driveIntegrationService.create(driveIntegration);

            //ENVIANDO EMAIL COM DADOS
            Map<String, Object> props = new HashMap<>();
            props.put("name", collaborator.getPerson().getName());
            props.put("email", collaborator.getUser().getEmail());
            props.put("password", password);

            emailLogServiceImp.sendHtmlEmail(
                    collaborator, EmailLog.EmailType.NEW_USER,
                    "Bem-vindo ao sistema PontoDoc",
                    "welcome-collaborator",
                    props);

            return mapper.toDTO(newCollaborator);

        } catch (Exception e){
            log.error("Ouve algum erro na criação de um novo colaborador");
            throw new RuntimeException();
        }

    }

    @Override
    public CollaboratorDTO update(Long id, Collaborator collaborator) {
        Collaborator collaboratorToUpdate = collaboratorRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Collaborator not found with id: " + id));

        if(!Objects.equals(collaborator.getPerson(), collaboratorToUpdate.getPerson())){
            Person personUpdated = personService.update(collaboratorToUpdate.getPerson().getId(), collaborator.getPerson());
            collaboratorToUpdate.setPerson(personUpdated);
        }

        if(!Objects.equals(collaborator.getUser(), collaboratorToUpdate.getUser())){
            User userUpdated = userService.update(collaboratorToUpdate.getUser().getId(), collaborator.getUser());
            collaboratorToUpdate.setUser(userUpdated);
        }

        collaboratorToUpdate.setUpdatedAt(LocalDateTime.now());
        Collaborator updatedCollaborator = collaboratorRepository.save(collaboratorToUpdate);
        return mapper.toDTO(updatedCollaborator);
    }


    @Override
    public CollaboratorDTO delete(Long id) {
        Collaborator collaborator = collaboratorRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Collaborator not found with id: " + id));
        collaborator.setDeletedAt(LocalDateTime.now());

        personService.delete(collaborator.getPerson().getId());
        userService.delete(collaborator.getUser().getId());

        Collaborator deletedCollaborator = collaboratorRepository.save(collaborator);
        return mapper.toDTO(deletedCollaborator);
    }

    private DriveIntegration createFolder(String folderId, Collaborator collaborator){
        DriveIntegration driveIntegration = new DriveIntegration();
        driveIntegration.setCollaborator(collaborator);
        driveIntegration.setFolderId(folderId);

        return driveIntegration;
    }

    public List<Collaborator> getAllNamesOfActivesCollaborators(){
        return this.collaboratorRepository.gellAllNames();
    }


    public List<CollaboratorAndDocumentsToSignDTO> findCollaboratorsToSignDoc(){
        return collaboratorRepository.findCollaboratorsToSignDoc();
    }


}
