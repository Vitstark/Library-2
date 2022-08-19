package ru.vitstark.library.dao;

import org.example.models.Book;
import org.example.models.Person;

import java.util.List;

public interface BookDAO extends CRUD<Long, Book>{
    List<Book> findBooksOfReaderOrderByName(Person person);
}
