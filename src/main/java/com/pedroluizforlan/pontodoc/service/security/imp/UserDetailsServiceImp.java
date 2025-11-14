package com.pedroluizforlan.pontodoc.service.security.imp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.config.security.UserAuthenticated;
import com.pedroluizforlan.pontodoc.repository.UserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService{

    private final UserRepository userRepository;

    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByEmail(username)
            .map(UserAuthenticated::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
}
