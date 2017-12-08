package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Author;
import wad.repository.AuthorRepository;

@Controller
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/authors")
    public String list(Model model) {
        model.addAttribute("authors", authorRepository.findAll());

        return "/authors";
    }

    @PostMapping("/authors")
    public String addAuthor(@RequestParam String name) {
        Author author = new Author(name);
        authorRepository.save(author);
        return "redirect:/";
    }

}
