package com.pedroluizforlan.pontodoc.config.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pedroluizforlan.pontodoc.model.User;

public class UserAuthenticated implements UserDetails {

    private final User user;

    public UserAuthenticated(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (user.getUseType()) {
          case MANAGER -> List.of(
              new SimpleGrantedAuthority("ROLE_MANAGER"),
              new SimpleGrantedAuthority("ROLE_ASSESSOR"),
              new SimpleGrantedAuthority("ROLE_EMPLOYEE")
          );
          case ASSESSOR -> List.of(
              new SimpleGrantedAuthority("ROLE_ASSESSOR"),
              new SimpleGrantedAuthority("ROLE_EMPLOYEE")
          );
          case EMPLOYEE -> List.of(
              new SimpleGrantedAuthority("ROLE_EMPLOYEE")
          );
      };
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
    
}
