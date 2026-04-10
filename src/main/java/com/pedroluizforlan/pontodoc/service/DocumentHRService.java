package com.pedroluizforlan.pontodoc.service;

import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.model.dto.DocumentHrDTO;

import java.util.List;

public interface DocumentHRService extends Crud<Long, DocumentHR>{
    List<DocumentHrDTO>  findDocumentsToSignByPerson();
}
