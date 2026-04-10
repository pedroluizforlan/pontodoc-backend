package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import com.pedroluizforlan.pontodoc.model.dto.DocumentHrDTO;
import com.pedroluizforlan.pontodoc.service.DocumentHRService;
import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.repository.DocumentHrRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DocumentHRServiceImp implements DocumentHRService {

    private final DocumentHrRepository documentHrRepository;
    
    @Override
    public List<DocumentHR> findAll() {
       return documentHrRepository.findAll();
    }

    @Override
    public DocumentHR findById(Long id) {
        return documentHrRepository.getReferenceById(id);
    }

    @Override
    public DocumentHR create(DocumentHR entity) {
        return this.documentHrRepository.save(entity);
    }

    @Override
    public DocumentHR update(Long id, DocumentHR entity) {
        var documentHrToUpdate = this.findById(id);

        return documentHrRepository.save(documentHrToUpdate);
    }

    @Override
    public DocumentHR delete(Long id) {
        var documentHrToDelete = this.findById(id);
        documentHrToDelete.setDeletedAt(LocalDateTime.now());
        return documentHrRepository.save(documentHrToDelete);
    }

    public List<DocumentHrDTO> findDocumentsToSignByPerson(){
        return documentHrRepository.findDocumentsToSignByCollaborator();
    }
    
}







