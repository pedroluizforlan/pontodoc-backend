package com.pedroluizforlan.pontodoc.model.dto;

public record UserDTO(
    Long id,
    String email,
    String useType,
    boolean verifiedEmail
) {}

