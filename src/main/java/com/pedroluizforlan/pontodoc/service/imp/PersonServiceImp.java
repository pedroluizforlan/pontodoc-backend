package com.pedroluizforlan.pontodoc.service.imp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.pedroluizforlan.pontodoc.model.Person;
import com.pedroluizforlan.pontodoc.repository.PersonRepository;
import com.pedroluizforlan.pontodoc.service.PersonService;

@Service
public class PersonServiceImp implements PersonService {

    private final PersonRepository personRepository;

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
    public Person create(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        return personRepository.save(person);
    }

    /*
     * @TODO Validar quando o dado for == null
     */
    @Override
    public Person update(Long id, Person person) {
        Person personToUpdate = findById(id);

        if(!Objects.equals(personToUpdate.getName(), person.getName())){
            personToUpdate.setName(person.getName());
        }

        if(!Objects.equals(personToUpdate.getBirthday(), person.getBirthday())){
            personToUpdate.setBirthday(person.getBirthday());
        }

        if(!Objects.equals(personToUpdate.getAddress(), person.getAddress())){
            personToUpdate.setAddress(person.getAddress());
        }

        if(!Objects.equals(personToUpdate.getNumber(), person.getNumber())){
            personToUpdate.setNumber(person.getNumber());
        }

        if(!Objects.equals(personToUpdate.getCep(), person.getCep())){
            personToUpdate.setCep(person.getCep());
        }

        if(!Objects.equals(personToUpdate.getCpf(), person.getCpf())){
            personToUpdate.setCpf(person.getCpf());
        }

        if(!Objects.equals(personToUpdate.getGender(), person.getGender())){
            personToUpdate.setGender(person.getGender());
        }

        personToUpdate.setUpdatedAt(LocalDateTime.now());
        return personRepository.save(personToUpdate);
    }

    @Override
    public Person delete(Long id) {
        Person person = findById(id);
        person.setDeleatedAt(LocalDateTime.now());
        return personRepository.save(person);
    }
    
}
