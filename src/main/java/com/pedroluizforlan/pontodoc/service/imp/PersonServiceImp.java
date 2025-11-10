package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;

import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.repository.PersonRepository;
import com.pedroluizforlan.pontodoc.service.PersonService;

public class PersonServiceImp implements PersonService {

    private PersonRepository personRepository;

    public PersonServiceImp(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        var persons = personRepository.findAll();
        return persons;
    }

    @Override
    public Person findById(Long id) {
        return personRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
    }

    @Override
    public Person create(Person entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public Person update(Long id, Person entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Person delete(Long id) {
        Person person = findById(id);
        person.setDeleatedAt(LocalDateTime.now());
        return personRepository.save(person);
    }
    
}
