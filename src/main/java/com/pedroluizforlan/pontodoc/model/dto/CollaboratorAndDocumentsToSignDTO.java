package com.pedroluizforlan.pontodoc.model.dto;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.DocumentHR;

import java.util.List;

public record CollaboratorAndDocumentsToSignDTO(
        Collaborator collaborator,
        DocumentHR document
) {
}
