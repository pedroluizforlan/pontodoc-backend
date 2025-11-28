package com.pedroluizforlan.pontodoc.model.dto;

import java.time.LocalDate;

public record PersonDTO(
    Long id,
    String name,
    String cpf,
    LocalDate birthday,
    String gender,
    String number,
    String address,
    String cep
) {}
