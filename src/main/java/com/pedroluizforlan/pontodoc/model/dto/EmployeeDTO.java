package com.pedroluizforlan.pontodoc.model.dto;

import java.time.LocalDate;

public record EmployeeDTO(
    Long id,
    String jobTitle,
    String department,
    String pis,
    LocalDate hiringDate,
    Long managerId,         
    String managerName,     
    boolean leadership
) {}

