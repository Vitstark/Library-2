package ru.vitstark.library.controllers;

import org.example.dao.BookDAO;
import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private BookDAO bookDAO;

    @GetMapping()
    public String people(Model model) {
        model.addAttribute("people", personDAO.findAllOrderedByName());
        return "people/people";
    }

    @GetMapping("/{id}")
    public String person(@PathVariable Long id, Model model) {
        Person person = personDAO.findByID(id).get();
        model.addAttribute("person", person);
        model.addAttribute("booksOfPerson", bookDAO.findBooksOfReaderOrderByName(person));
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

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable Long id, Model model) {
        model.addAttribute("person", personDAO.findByID(id).get());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(person);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Long id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
