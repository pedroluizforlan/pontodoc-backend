package com.pedroluizforlan.pontodoc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroluizforlan.pontodoc.model.EmailLog;
import com.pedroluizforlan.pontodoc.service.imp.EmailLogServiceImp;

@RestController
@RequestMapping("api/email")
public class EmailController {
    
    private final EmailLogServiceImp emailLogService;

    public EmailController(EmailLogServiceImp emailLogService){
        this.emailLogService = emailLogService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailLog emailLog){
        emailLogService.sendEmail(emailLog);
    }
    
}
