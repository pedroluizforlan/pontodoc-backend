package com.pedroluizforlan.pontodoc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.repository.PersonRepository;
import com.pedroluizforlan.pontodoc.service.imp.PersonServiceImp;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImp personService;


    @BeforeEach
    void setUp(){}
    
    // @Test
    // void shouldThrowBusinessExceptionIfManagerIsNotLidership(){
    //     Person person = new Person();
    //     Person leadership = new Person();
    //     leadership.setLeadership(false);
    //     person.setManagerId(leadership);

    //     when();

    // }
}
