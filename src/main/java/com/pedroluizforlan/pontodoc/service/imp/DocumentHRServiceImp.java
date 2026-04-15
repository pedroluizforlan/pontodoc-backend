package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.EmailLog;
import com.pedroluizforlan.pontodoc.model.SignatureRequest;
import com.pedroluizforlan.pontodoc.model.dto.CollaboratorAndDocumentsToSignDTO;
import com.pedroluizforlan.pontodoc.model.dto.DocumentHrDTO;
import com.pedroluizforlan.pontodoc.service.CollaboratorService;
import com.pedroluizforlan.pontodoc.service.DocumentHRService;
import com.pedroluizforlan.pontodoc.service.SignatureRequestService;
import com.pedroluizforlan.pontodoc.util.DocumentsUtils;
import com.pedroluizforlan.pontodoc.util.TokenUtils;
import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.repository.DocumentHrRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DocumentHRServiceImp implements DocumentHRService {

    private final DocumentHrRepository documentHrRepository;
    private final CollaboratorService collaboratorService;
    private final EmailLogServiceImp emailLogServiceImp;
    private final SignatureRequestService signatureRequestService;
    
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

    public void sendEmailToCollaboratorToSignDoc(){
        var collaboratorsToSignDoc = collaboratorService.findCollaboratorsToSignDoc();

        Map<Collaborator,List<DocumentHR>> map = new HashMap<>();

        for(CollaboratorAndDocumentsToSignDTO c : collaboratorsToSignDoc){

            if(map.containsKey(c.collaborator())){
                var find = map.get(c.collaborator());
                find.add(c.document());
            } else {
                List<DocumentHR> listOfDoc = new ArrayList<>();
                listOfDoc.add(c.document());
                map.put(c.collaborator(), listOfDoc);
            }
        }

        for(Collaborator collaborator : map.keySet()){
            String token = TokenUtils.generateToken();
            SignatureRequest signatureRequest = new SignatureRequest();
            signatureRequest.setDocumentHR(map.get(collaborator));
            signatureRequest.setCollaborator(collaborator);
            signatureRequest.setToken(token);

            Map<String, Object> props = new HashMap<>();
            List<String> documents = map.get(collaborator)
                    .stream().map(documentHR -> documentHR.getDocumentType().toString())
                    .filter(s -> !s.equals("OUTROS"))
                    .toList();

            props.put("name", collaborator.getPerson().getName());
            props.put("email", collaborator.getUser().getEmail());
            props.put("documents", documents);
            props.put("url", token);

            signatureRequestService.create(signatureRequest);

            emailLogServiceImp.sendHtmlEmail(
                    collaborator,
                    EmailLog.EmailType.SIGN_DOC,
                    "Você possuí documentos para serem assinados!",
                    "docs_to_sign",
                    props);

            break;
        }

    }


    
}







