package ru.vitstark.library.controllers;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vitstark.library.models.Book;
import ru.vitstark.library.models.Person;
import ru.vitstark.library.services.PersonService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonService personService;

    @Autowired
    public PeopleController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public String people(Model model) {
        model.addAttribute("people", personService.findAllOrderByName());
        return "people/people";
    }

    @GetMapping("/{id}")
    public String person(@PathVariable Long id, Model model) {
        Person person = personService.findById(id).get();

        List<Book> books = person.getBooks();
        Date now = new Date();

        for (Book book : books) {
            Date dateOfOverdue = book.getDateOfBorrow();
            dateOfOverdue.setDate(dateOfOverdue.getDate() + 7);
            book.setOverdue(now.after(dateOfOverdue));
        }

        model.addAttribute("person", person);
        model.addAttribute("booksOfPerson", books);
        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String savePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable Long id, Model model) {
        model.addAttribute("person", personService.findById(id).get());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personService.update(person);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
