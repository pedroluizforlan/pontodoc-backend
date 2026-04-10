package com.pedroluizforlan.pontodoc.model.dto;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.model.DocumentsBatch;

import java.time.LocalDateTime;

public record DocumentHrDTO(
    Long Id,
    LocalDateTime createdAt,
    String documentHash,
    DocumentHR.DocumentType documentType,
    String path,
    DocumentHR.Status status,
    LocalDateTime updatedAt,
    Long collaboratorId,
    String collaboratorName,
    Long batchId,
    DocumentHR.Attribution attribution
) {

}
