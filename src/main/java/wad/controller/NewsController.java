package wad.controller;

import wad.domain.FileObject;
import wad.domain.NewsItem;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.domain.Author;
import wad.domain.Category;
import wad.repository.AuthorRepository;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;
import wad.service.FileService;
import wad.service.NewsService;

/**
 *Class for managing listing and adding news
 *
 */
@Controller
public class NewsController {

       /**
     * A repository for NewsItem objects
     */
    @Autowired
    private NewsRepository newsRepository;

        /**
     * A repository for Category objects
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * A service managing FileObjects
     */
    @Autowired
    FileService fileService;

    /**
     * A service for managing adding NewsItems to Categories and Authors,
     * also creates lists of NewsItems
     */
    @Autowired
    NewsService newsService;

    /**
     * A repository for Author objects
     */
    @Autowired
    AuthorRepository authorRepository;
    
//    public NewsController() {
//        Category category1 = new Category("Kissat");
//        categoryRepository.save(category1);
//        Category category2 = new Category("Kasvit");
//        categoryRepository.save(category2);
//    }
    
    /**
     * Method for showing news on the front page
     * @param model
     * @return the front/home page
     */
    @GetMapping("/")
    public String list(Model model) {    
        List<NewsItem> fiveNewest = newsService.getFiveNewestNews();
        
        model.addAttribute("selectednews", fiveNewest);
        model.addAttribute("oldernews", newsService.getOlderNews(fiveNewest));       
        model.addAttribute("categories", categoryRepository.findAll());

        return "home";
    }

    /**
     * Method for adding a NewsItem via a form
     * @param title
     * @param ingress
     * @param newsText
     * @param authors
     * @param categories
     * @param file
     * @return
     * @throws IOException 
     */
    @PostMapping("/news")
    public String addNews(@RequestParam String title,
            @RequestParam String ingress, @RequestParam String newsText,
            @RequestParam(value = "authors[]") String[] authors,
            @RequestParam(value = "categories[]") String[] categories,
            @RequestParam("file") MultipartFile file) throws IOException {

        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(title);
        newsItem.setIngress(ingress);
        newsItem.setNewsText(newsText);

        List<Author> authorList = newsService.createAuthorList(authors);
        List<Category> categoryList = newsService.createCategoryList(categories);

        newsItem.setAuthors(authorList);
        newsItem.setCategories(categoryList);
        newsItem.setPicture(fileService.createFile(file));

        newsRepository.save(newsItem);
        newsService.assignNewsItemToAuthors(newsItem, authorList);
        newsService.assignNewsItemToCategories(newsItem, categoryList);

        return "redirect:/";
    }

    /**
     * Method for displaying available authors and categories in the form
     * @param model
     * @return a page containing the form for adding news
     */
    @GetMapping("/news")
    public String setAuthorsAndCategories(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "news";
    }

//    @PostMapping("/news")
//    public String addNews(@Valid @ModelAttribute NewsItem newsItem, 
//            @RequestParam("file") MultipartFile file) throws IOException {
//                FileObject fo = new FileObject();
//        fo.setName(file.getOriginalFilename());
//        fo.setContentType(file.getContentType());
//        fo.setContentLength(file.getSize());
//        fo.setContent(file.getBytes());
//        fileRepository.save(fo);
//        newsItem.setPicture(fo);
//        newsRepository.save(newsItem);
//        return "redirect:/";
//    }
    /**
     * Method for showing an individual NewsItem, also shows a list of other news
     * in the same categories
     * @param model
     * @param newsItemId Long, id of the selected NewsItem
     * @return a page containing the selected NewsItem
     */
    @GetMapping("/newsItem/{newsItemId}")
    public String getNewsItem(Model model, @PathVariable Long newsItemId) {
        NewsItem newsItem = newsRepository.getOne(newsItemId);
        model.addAttribute("newsItem", newsItem);
        model.addAttribute("categories", newsItem.getCategories());
        model.addAttribute("authors", newsItem.getAuthors());
        model.addAttribute("otherNews", newsService.createOtherNewsList(newsItem));
        return "newsItem";
    }
}
