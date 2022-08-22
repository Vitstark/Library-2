package ru.vitstark.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vitstark.library.models.Book;
import ru.vitstark.library.models.Person;
import ru.vitstark.library.services.BookService;
import ru.vitstark.library.services.PersonService;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;
    @Autowired
    private PersonService personService;

    @GetMapping()
    public String books(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/books";
    }

    @GetMapping("/search")
    public String books(Model model, @RequestParam("text") String beginningOfName) {
        model.addAttribute("books", bookService.findByNameStartingWith(beginningOfName));
        return "books/books";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable("id") Long id, @ModelAttribute("person") Person person, Model model) {
        Book book = bookService.findById(id).get();
        model.addAttribute("book", book);

        Person reader = book.getReader();
        if (reader == null) {
            model.addAttribute("people", personService.findAllOrderByName());
        } else {
            model.addAttribute("reader", book.getReader());
        }

        return "books/book";
    }

    @PatchMapping("/{id}/free_book")
    public String freeBook(@PathVariable("id") Long id) {
        Book book = bookService.findById(id).get();

        book.setReader(null);
        book.setDateOfBorrow(null);

        bookService.update(book);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/appoint_book")
    public String appointBook(@PathVariable("id") Long bookId, @ModelAttribute("person") Person person) {
        Book book = bookService.findById(bookId).get();

        book.setReader(person);
        book.setDateOfBorrow(new Date());

        bookService.update(book);
        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String saveBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("book", bookService.findById(id).get());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        bookService.update(book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }
}
