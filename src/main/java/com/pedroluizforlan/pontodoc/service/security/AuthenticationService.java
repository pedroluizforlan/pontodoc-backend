package com.pedroluizforlan.pontodoc.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    
    public String authenticate(Authentication auth){
        return jwtService.generateToken(auth);
    }
}
