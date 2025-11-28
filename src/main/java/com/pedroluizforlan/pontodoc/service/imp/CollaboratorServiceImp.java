package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.DriveIntegration;
import com.pedroluizforlan.pontodoc.model.EmailLog;
import com.pedroluizforlan.pontodoc.model.Employee;
import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.model.User;
import com.pedroluizforlan.pontodoc.repository.CollaboratorRepository;
import com.pedroluizforlan.pontodoc.service.CollaboratorService;
import com.pedroluizforlan.pontodoc.service.DriveIntegrationService;
import com.pedroluizforlan.pontodoc.service.EmployeeService;
import com.pedroluizforlan.pontodoc.service.PersonService;
import com.pedroluizforlan.pontodoc.service.UserService;
import com.pedroluizforlan.pontodoc.service.integrations.GoogleDriveService;


@Service
public class CollaboratorServiceImp implements CollaboratorService{

    

    private final CollaboratorRepository collaboratorRepository;
    private final PersonService personService;
    private final EmployeeService employeeService;
    private final UserService userService;
    private final EmailLogServiceImp emailLogServiceImp;
    private final DriveIntegrationService driveIntegrationService;
    private final GoogleDriveService googleDriveService;

    public CollaboratorServiceImp(
        CollaboratorRepository collaboratorRepository,
        PersonService personService,
        EmployeeService employeeService,
        UserService userService,
        EmailLogServiceImp emailLogServiceImp,
        DriveIntegrationService driveIntegrationService,
        GoogleDriveService googleDriveService
        ){
        this.collaboratorRepository = collaboratorRepository;
        this.personService = personService;
        this.employeeService = employeeService;
        this.userService = userService;
        this.emailLogServiceImp = emailLogServiceImp;
        this.driveIntegrationService = driveIntegrationService;
        this.googleDriveService = googleDriveService;
    }

    @Override
    public List<Collaborator> findAll() {
        var collaborators = collaboratorRepository.findAll();
        var collaboratorsReturn = collaborators
                                    .stream()
                                    .filter(collaborator -> !collaborator.getUser().getUseType().equals("MANAGER"))
                                    .toList();                        
        return collaboratorsReturn;
    }

    @Override
    public Collaborator findById(Long id) {
        return collaboratorRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Collaborator not found with id: " + id));
    }

    @Override
    public Collaborator create(Collaborator collaborator) {
        Person cPerson = personService.create(collaborator.getPerson());
        Employee cEmployee = employeeService.create(collaborator.getEmployee());
        User cUser = userService.create(collaborator.getUser());

        collaborator.setPerson(cPerson);
        collaborator.setEmployee(cEmployee);
        collaborator.setUser(cUser);

        collaborator.setCreatedAt(LocalDateTime.now());
        
        var newCollaborator = collaboratorRepository.save(collaborator);

        var email = this.createEmailLog(collaborator);


        var folderId = googleDriveService.createFolderForCollaborator(collaborator.getPerson().getName());
        var driveIntegration = this.createFolder(folderId, newCollaborator);

        driveIntegrationService.create(driveIntegration);
        emailLogServiceImp.sendEmail(email);

        return newCollaborator;
    }

    @Override
    public Collaborator update(Long id, Collaborator collaborator) {
        Collaborator collaboratorToUpdate = findById(id);

        if(!Objects.equals(collaborator.getEmployee(), collaboratorToUpdate.getEmployee())){
            Employee employeeUpdated = employeeService.update(collaboratorToUpdate.getEmployee().getId(), collaborator.getEmployee());
            collaboratorToUpdate.setEmployee(employeeUpdated);
        }

        if(!Objects.equals(collaborator.getPerson(), collaboratorToUpdate.getPerson())){
            Person personUpdated = personService.update(collaboratorToUpdate.getPerson().getId(), collaborator.getPerson());
            collaboratorToUpdate.setPerson(personUpdated);
        }

        if(!Objects.equals(collaborator.getUser(), collaboratorToUpdate.getUser())){
            User userUpdated = userService.update(collaboratorToUpdate.getUser().getId(), collaborator.getUser());
            collaboratorToUpdate.setUser(userUpdated);
        }

        collaboratorToUpdate.setUpdatedAt(LocalDateTime.now());
        return collaboratorRepository.save(collaboratorToUpdate);
    }


    @Override
    public Collaborator delete(Long id) {
        Collaborator collaborator = findById(id);
        collaborator.setDeleatedAt(LocalDateTime.now());

        personService.delete(collaborator.getPerson().getId());
        userService.delete(collaborator.getUser().getId());
        employeeService.delete(collaborator.getEmployee().getId());


        return collaboratorRepository.save(collaborator);
    }


    private EmailLog createEmailLog(Collaborator collaborator){
        EmailLog emailLog = new EmailLog();

        emailLog.setCollaborator(collaborator);
        emailLog.setEmailType("NEW USER");
        emailLog.setEmailSubject("Bem-vindo ao sistema PontoDoc");
        emailLog.setEmailBody("<h1>Seja bem vindo ao Sistema PontoDoc </h1>\n"
        + "Seu usuário: " +collaborator.getUser().getEmail() + 
        "\nSua senha: " +collaborator.getUser().getPassword()
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
