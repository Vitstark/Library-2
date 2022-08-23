package ru.vitstark.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.vitstark.library.models.Book;
import ru.vitstark.library.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findByNameStartingWith(String str) {
        return bookRepository.findByNameStartingWith(str);
    }

    public List<Book> findAllOrderByName() {
        return bookRepository.findAll(Sort.by("name"));
    }

    public List<Book> findAllOrderByDate() {
        return bookRepository.findAll(Sort.by("date"));
    }

    public List<Book> findPageOrderByDate(int page, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("date"))).getContent();
    }

    public List<Book> findPage(int page, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public void update(Book book) {
        bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
