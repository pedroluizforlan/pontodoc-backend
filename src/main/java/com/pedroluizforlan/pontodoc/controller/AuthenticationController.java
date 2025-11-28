package com.pedroluizforlan.pontodoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pedroluizforlan.pontodoc.dto.AuthResponse;
import com.pedroluizforlan.pontodoc.dto.LoginRequest;
import com.pedroluizforlan.pontodoc.service.security.AuthenticationService;

@RestController
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    
    
    @PostMapping("api/auth")
    public AuthResponse authenticate(@RequestBody LoginRequest loginRequest){
        String token = authenticationService.authenticate(loginRequest);
        return new AuthResponse(token);
    }
}
