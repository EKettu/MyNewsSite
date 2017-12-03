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
import wad.repository.NewsRepository;

@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/")
    public String list(Model model) {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "newsDate");
        Page<NewsItem> selectedNews =  newsRepository.findAll(pageable);
       // model.addAttribute("selectednews", newsRepository.findAll(pageable));
       model.addAttribute("selectednews", selectedNews);
        List<NewsItem> allNews = newsRepository.findAll();
        List<NewsItem> olderNews = new ArrayList<>();
        for (int i = 5; i < allNews.size(); i++) {
            olderNews.add(allNews.get(i));
        }
        model.addAttribute("oldernews", olderNews);
      //  model.addAttribute("allnews", newsRepository.findAll());
        return "home";
    }

    
       @PostMapping("/news")
    public String addNews(@Valid @ModelAttribute NewsItem newsItem) {
       newsRepository.save(newsItem);
        return "redirect:/";
}

    @GetMapping("/newsItem/{newsItemId}")
    public String getNewsItem(Model model, @PathVariable Long newsItemId) {
        NewsItem newsItem = newsRepository.getOne(newsItemId);
        model.addAttribute("newsItem", newsItem);

        return "newsItem";
    }
}
