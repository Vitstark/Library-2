package ru.vitstark.library.dao;

import org.example.models.Person;

import java.util.List;

public interface PersonDAO extends CRUD<Long, Person> {
    List<Person> findAllOrderedByName();
}
