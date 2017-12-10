package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Author;
import wad.repository.AuthorRepository;
/**
 * Class for managing the authors of news articles
 * 
 */
@Controller
public class AuthorController {

    /**
     * A repository for Author objects
     */
    @Autowired
    AuthorRepository authorRepository;
    /**
     * Method for listing Author objects
     * @param model
     * @return a page with a list of authors
     */
    @GetMapping("/authors")
    public String list(Model model) {
        model.addAttribute("authors", authorRepository.findAll());

        return "/authors";
    }
    
    /**
     * Method for adding Author objects
     * @param name String, name of the author 
     * @return redirect to the home page
     */
    @PostMapping("/authors")
    public String addAuthor(@RequestParam String name) {
        Author author = new Author(name);
        authorRepository.save(author);
        return "redirect:/";
    }

}
