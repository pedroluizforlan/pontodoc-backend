package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

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


@Service
public class CollaboratorServiceImp implements CollaboratorService{

    private final CollaboratorRepository collaboratorRepository;
    private final PersonService personService;
    private final UserService userService;
    private final EmailLogServiceImp emailLogServiceImp;
    private final DriveIntegrationService driveIntegrationService;
    private final GoogleDriveService googleDriveService;
    private final CollaboratorMapper mapper;

    public CollaboratorServiceImp(
        CollaboratorRepository collaboratorRepository,
        PersonService personService,
        UserService userService,
        EmailLogServiceImp emailLogServiceImp,
        DriveIntegrationService driveIntegrationService,
        GoogleDriveService googleDriveService,
        CollaboratorMapper mapper
        ){
        this.collaboratorRepository = collaboratorRepository;
        this.personService = personService;
        this.userService = userService;
        this.emailLogServiceImp = emailLogServiceImp;
        this.driveIntegrationService = driveIntegrationService;
        this.googleDriveService = googleDriveService;
        this.mapper = mapper;
    }

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
    public CollaboratorDTO create(Collaborator collaborator) {
        Person cPerson = personService.create(collaborator.getPerson());
        
        var password = collaborator.getUser().getPassword();
        User cUser = userService.create(collaborator.getUser());
        
        collaborator.setPerson(cPerson);
        collaborator.setUser(cUser);

        collaborator.setCreatedAt(LocalDateTime.now());
        
        var newCollaborator = collaboratorRepository.save(collaborator);

        var email = this.createEmailLog(collaborator, password);


        var folderId = googleDriveService.createFolderForCollaborator(collaborator.getPerson().getName());
        var driveIntegration = this.createFolder(folderId, newCollaborator);

        driveIntegrationService.create(driveIntegration);
        emailLogServiceImp.sendEmail(email);

        var collaboratorDTO = mapper.toDTO(newCollaborator);
        return collaboratorDTO;
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


    private EmailLog createEmailLog(Collaborator collaborator, String password){
        EmailLog emailLog = new EmailLog();

        emailLog.setCollaborator(collaborator);
        emailLog.setEmailType("NEW USER");
        emailLog.setEmailSubject("Bem-vindo ao sistema PontoDoc");
        emailLog.setEmailBody("Seja bem vindo ao Sistema PontoDoc\n"
        + "Seu usuário: " +collaborator.getUser().getEmail() + 
        "\nSua senha: " + password
        );

        return emailLog;
    }

    private DriveIntegration createFolder(String folderId, Collaborator collaborator){
        DriveIntegration driveIntegration = new DriveIntegration();
        driveIntegration.setCollaborator(collaborator);
        driveIntegration.setFolderId(folderId);

        return driveIntegration;
    }
    
}
