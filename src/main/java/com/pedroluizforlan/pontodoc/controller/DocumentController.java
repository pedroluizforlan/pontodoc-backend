package com.pedroluizforlan.pontodoc.controller;

import com.pedroluizforlan.pontodoc.model.Collaborator;
import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.model.dto.DocumentHrDTO;
import com.pedroluizforlan.pontodoc.service.DocumentHRService;
import com.pedroluizforlan.pontodoc.service.FileStorageService;
import com.pedroluizforlan.pontodoc.service.SignatureRequestService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pedroluizforlan.pontodoc.service.DocumentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentHRService documentHRService;
    private final SignatureRequestService signatureRequestService;
    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> batchDocuments(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        documentService.storeDocument(file);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentHrDTO>> getDocumentsToSignByPerson(){
        return ResponseEntity.ok(documentHRService.findDocumentsToSignByPerson());
    }


    @GetMapping("/sendmail")
    public ResponseEntity<String> test(){
        documentHRService.sendEmailToCollaboratorToSignDoc();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/teste")
    public ResponseEntity<List<String>> getDocsToSign(@RequestParam("token") String token){
        System.out.println("TA BATENDO");
        return ResponseEntity.ok(signatureRequestService.getDocumentsToSign(token));
    }

    @GetMapping("/arquivo")
    public ResponseEntity<?> getDoc(@RequestParam("path") String path){

        InputStream inputStream = fileStorageService.get(path);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"arquivo.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }

}
