package com.pedroluizforlan.pontodoc.service.imp;

import com.pedroluizforlan.pontodoc.model.DocumentSinged;
import com.pedroluizforlan.pontodoc.repository.DocumentSignedRepository;
import com.pedroluizforlan.pontodoc.service.DocumentSignedService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


//@TODO MELHORAR SERVICE
@Service
public class DocumentSignedServiceImp implements DocumentSignedService {

    private DocumentSignedRepository documentSignedRepository;
    @Override
    public List<DocumentSinged> findAll() {
        return documentSignedRepository.findAll();
    }

    @Override
    public DocumentSinged findById(Long aLong) {
        return documentSignedRepository
                .findById(aLong)
                .orElseThrow(()-> new RuntimeException("Documento não encontrado"));
    }

    @Override
    public DocumentSinged create(DocumentSinged entity) {
        return documentSignedRepository.save(entity);
    }


    @Override
    public DocumentSinged update(Long aLong, DocumentSinged entity) {
        var documentToUpdate = this.findById(aLong);

        return documentToUpdate;
    }

    @Override
    public DocumentSinged delete(Long aLong) {
        var documentToDelete = this.findById(aLong);

        documentToDelete.setDeletedAt(LocalDateTime.now());

        return this.documentSignedRepository.save(documentToDelete);
    }

}
