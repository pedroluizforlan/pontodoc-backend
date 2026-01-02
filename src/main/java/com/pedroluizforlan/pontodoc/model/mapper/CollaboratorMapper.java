package com.pedroluizforlan.pontodoc.model.mapper;

import org.springframework.stereotype.Component;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.model.User;
import com.pedroluizforlan.pontodoc.model.dto.CollaboratorDTO;
import com.pedroluizforlan.pontodoc.model.dto.PersonDTO;
import com.pedroluizforlan.pontodoc.model.dto.UserDTO;

@Component
public class CollaboratorMapper {

    public CollaboratorDTO toDTO(Collaborator c) {

        Person p = c.getPerson();
        User u = c.getUser();

        PersonDTO personDTO = new PersonDTO(
                p.getId(),
                p.getName(),
                p.getCpf(),
                p.getBirthday(),
                p.getGender(),
                p.getNumber(),
                p.getAddress(),
                p.getCep(),
                p.getJobTitle(),
                p.getDepartment(),
                p.getPis(),
                p.getHiringDate(),
                p.getManagerId() != null ? p.getManagerId().getId() : null,
                p.isLeadership()
        );

        UserDTO userDTO = new UserDTO(
                u.getId(),
                u.getEmail(),
                u.getUseType().toString(),
                u.isVerifiedEmail());

        return new CollaboratorDTO(
                c.getId(),
                personDTO,
                userDTO
        );
    }
}
