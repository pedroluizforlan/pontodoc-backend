package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.Employee;
import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.model.User;
import com.pedroluizforlan.pontodoc.repository.CollaboratorRepository;
import com.pedroluizforlan.pontodoc.service.CollaboratorService;
import com.pedroluizforlan.pontodoc.service.EmployeeService;
import com.pedroluizforlan.pontodoc.service.PersonService;
import com.pedroluizforlan.pontodoc.service.UserService;

@Service
public class CollaboratorServiceImp implements CollaboratorService{

    private final CollaboratorRepository collaboratorRepository;
    private final PersonService personService;
    private final EmployeeService employeeService;
    private final UserService userService;

    public CollaboratorServiceImp(
        CollaboratorRepository collaboratorRepository,
        PersonService personService,
        EmployeeService employeeService,
        UserService userService
        ){
        this.collaboratorRepository = collaboratorRepository;
        this.personService = personService;
        this.employeeService = employeeService;
        this.userService = userService;
    }
    /*
     * @TODO Entender motivo de não estar listando
     */
    @Override
    public List<Collaborator> findAll() {
        var collaborators = collaboratorRepository.findAll();
        return collaborators;
    }

     /*
     * @TODO Entender motivo de não estar listando
     */
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
        return collaboratorRepository.save(collaborator);
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

    /*
    * @TODO Avaliar pq não está apagando
    */
    @Override
    public Collaborator delete(Long id) {
        Collaborator collaborator = findById(id);
        collaborator.setDeleatedAt(LocalDateTime.now());

        personService.delete(collaborator.getPerson().getId());
        userService.delete(collaborator.getUser().getId());
        employeeService.delete(collaborator.getEmployee().getId());


        return collaboratorRepository.save(collaborator);
    }
    
}
