package com.pedroluizforlan.pontodoc.model.dto;

public record CollaboratorDTO(
    Long id,
    PersonDTO person,
    UserDTO user,
    EmployeeDTO employee
) {}

