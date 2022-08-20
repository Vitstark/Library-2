package ru.vitstark.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vitstark.library.models.Person;
import ru.vitstark.library.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Person> findAllOrderByName() {
        return personRepository.findAll(Sort.by("name"));
    }

    public void save(Person person) {
        personRepository.save(person);
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public void update(Person Person) {
        personRepository.save(Person);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
