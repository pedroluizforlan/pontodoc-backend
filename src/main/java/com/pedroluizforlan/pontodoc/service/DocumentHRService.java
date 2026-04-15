package com.pedroluizforlan.pontodoc.service;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.model.dto.DocumentHrDTO;

import java.util.List;
import java.util.Map;

public interface DocumentHRService extends Crud<Long, DocumentHR>{
    List<DocumentHrDTO>  findDocumentsToSignByPerson(); //CRIAR FILTRO OPCIONAL PARA TRAZER TODOS QUE ESTÃO EM DIVIDA DE ASSINATURA

    void sendEmailToCollaboratorToSignDoc();
}
