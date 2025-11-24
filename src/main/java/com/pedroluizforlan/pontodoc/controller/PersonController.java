package com.pedroluizforlan.pontodoc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.service.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    
    private final PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<List<Person>> getAllPerson(){
        List<Person> persons = personService.findAll();
        return ResponseEntity.ok(persons);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){
        Person person = personService.findById(id);
        return ResponseEntity.ok(person);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person){
        return ResponseEntity.ok(personService.update(id, person));
    }
}
