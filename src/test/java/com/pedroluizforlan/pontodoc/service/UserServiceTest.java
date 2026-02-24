package com.pedroluizforlan.pontodoc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pedroluizforlan.pontodoc.model.User;
import com.pedroluizforlan.pontodoc.repository.UserRepository;
import com.pedroluizforlan.pontodoc.service.imp.UserServiceImp;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    
    @BeforeEach
    void setUp(){}
    
    @Test
    void shouldReturnUserWhenExists(){
        //Arange
        User user = new User();
        user.setId(1L);
        user.setEmail("pedro.forlan2000@gmail.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("pedro.forlan2000@gmail.com");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnEncryptedPassword(){
        User user = new User();
        user.setEmail("pedro.forlan2000@gmail.com");
        user.setPassword("SenhaForte123@");
        
        when(passwordEncoder.encode("SenhaForte@123"))
            .thenReturn("SenhaCriptografada");
        
        when(userRepository.save(any(User.class)))
            .thenAnswer(invocation -> invocation.getArguments());

        User result = userService.create(user);

        assertThat(result.getPassword()).isEqualTo("SenhaCriptografada");
        assertThat(result.getCreatedAt()).isNotNull();

        verify(passwordEncoder).encode("SenhaForte@123");
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowBusinessExceptionIfManagerIsNotLidership(){
        User user = new User();
    }
}
