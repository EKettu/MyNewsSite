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

@Controller
public class CategoryController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public String list(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());

        return "/categories";
    }

    @PostMapping("/categories")
    public String addCategory(@RequestParam String name) {
        Category category = new Category(name);
        categoryRepository.save(category);
        return "redirect:/";
    }

    @GetMapping("/category/{categoryId}")
    public String getCategory(Model model, @PathVariable Long categoryId) {
        Category category = categoryRepository.getOne(categoryId);

        model.addAttribute("category", category);
        model.addAttribute("news", category.getNews());

        return "category";
    }

}
