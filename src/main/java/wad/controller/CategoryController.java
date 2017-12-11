package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Category;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;
/**
 * Class for managing news categories
 * 
 */
@Controller
public class CategoryController {

    /**
     * A repository for NewsItem objects
     */
    @Autowired
    private NewsRepository newsRepository;

    /**
     * A repository for Category objects
     */
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Method for listing Category objects
     * @param model
     * @return a page with a list of categories
     */
    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());

        return "categories";
    }

    /**
     * Method for adding Category objects
     * @param name String, name of the category
     * @return redirect to the home page
     */
    @PostMapping("/categories")
    public String addCategory(@RequestParam String name) {
        Category category = new Category(name);
        categoryRepository.save(category);
        return "redirect:/";
    }
    
    /**
     * Method for showing news in an individual category
     * @param model
     * @param categoryId Long, id of the selected category
     * @return a web page of the selected category
     */
    @GetMapping("/category/{categoryId}")
    public String getCategory(Model model, @PathVariable Long categoryId) {
        Category category = categoryRepository.getOne(categoryId);

        model.addAttribute("category", category);
        model.addAttribute("news", category.getNews());

        return "category";
    }

}
