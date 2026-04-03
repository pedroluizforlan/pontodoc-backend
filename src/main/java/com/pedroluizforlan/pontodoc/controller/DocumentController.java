package com.pedroluizforlan.pontodoc.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pedroluizforlan.pontodoc.service.DocumentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    
    @PostMapping("/upload")
    public String batchDocuments(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        documentService.storeDocument(file);
        return "ok";
    }
    
    
}
