package com.pedroluizforlan.pontodoc.model.mapper;

import org.springframework.stereotype.Component;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.Employee;
import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.model.User;
import com.pedroluizforlan.pontodoc.model.dto.CollaboratorDTO;
import com.pedroluizforlan.pontodoc.model.dto.EmployeeDTO;
import com.pedroluizforlan.pontodoc.model.dto.PersonDTO;
import com.pedroluizforlan.pontodoc.model.dto.UserDTO;

@Component
public class CollaboratorMapper {

    public CollaboratorDTO toDTO(Collaborator c) {

        Person p = c.getPerson();
        User u = c.getUser();
        Employee e = c.getEmployee();

        PersonDTO personDTO = new PersonDTO(
            p.getId(),
            p.getName(),
            p.getCpf(),
            p.getBirthday(),
            p.getGender(),
            p.getNumber(),
            p.getAddress(),
            p.getCep()
        );

        UserDTO userDTO = new UserDTO(
            u.getId(),
            u.getEmail(),
            u.getUseType().toString(),
            u.isVerifiedEmail()
        );

        EmployeeDTO employeeDTO = null;

        if (e != null) {
            employeeDTO = new EmployeeDTO(
                e.getId(),
                e.getJobTitle(),
                e.getDepartment(),
                e.getPis(),
                e.getHiringDate(),
                e.getManagerId() != null ? e.getManagerId().getId() : null,
                e.getManagerId() != null ? e.getManagerId().getJobTitle() : null,
                e.isLeadership()
            );
        }

        return new CollaboratorDTO(
            c.getId(),
            personDTO,
            userDTO,
            employeeDTO
        );
    }
}
