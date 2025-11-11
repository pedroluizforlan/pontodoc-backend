package com.pedroluizforlan.pontodoc.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroluizforlan.pontodoc.service.security.AuthenticationService;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    
    @PostMapping("api/auth")
    public String authenticate(Authentication auth){
        return authenticationService.authenticate(auth);
    }
}
