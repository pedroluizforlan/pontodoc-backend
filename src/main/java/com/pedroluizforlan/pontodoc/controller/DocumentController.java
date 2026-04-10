package com.pedroluizforlan.pontodoc.controller;

import com.pedroluizforlan.pontodoc.model.DocumentHR;
import com.pedroluizforlan.pontodoc.model.dto.DocumentHrDTO;
import com.pedroluizforlan.pontodoc.service.DocumentHRService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pedroluizforlan.pontodoc.service.DocumentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentHRService documentHRService;

    @PostMapping("/upload")
    public ResponseEntity<?> batchDocuments(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        documentService.storeDocument(file);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentHrDTO>> getDocumentsToSignByPerson(){
        return ResponseEntity.ok(documentHRService.findDocumentsToSignByPerson());
    }

}
