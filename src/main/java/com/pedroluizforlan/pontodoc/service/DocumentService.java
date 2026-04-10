package com.pedroluizforlan.pontodoc.service;

import org.springframework.web.multipart.MultipartFile;



public interface DocumentService{

    void storeDocument(MultipartFile multipartFile);
    
} 
