package com.pedroluizforlan.pontodoc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.service.PersonService;

public class PersonController {
        private PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPerson(){
        List<Person> persons = personService.findAll();
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){
        Person person = personService.findById(id);
        return ResponseEntity.ok(person);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person){
        return ResponseEntity.ok(personService.update(id, person));
    }
}
