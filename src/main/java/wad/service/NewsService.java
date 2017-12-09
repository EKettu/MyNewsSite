package wad.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Author;
import wad.domain.Category;
import wad.domain.NewsItem;
import wad.repository.AuthorRepository;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Author> createAuthorList(String[] authors) {
        List<Author> authorsList = new ArrayList<>();
        for (int i = 0; i < authors.length; i++) {
            authorsList.add(new Author(authors[i]));
        }
        return authorsList;
    }

    public List<Category> createCategoryList(String[] categories) {
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            categoryList.add(new Category(categories[i]));
        }
        return categoryList;
    }

    public void assignNewsItemToAuthors(NewsItem newsItem, List<Author> authors) {
        for (int i = 0; i < authors.size(); i++) {
            Author author = authorRepository.findByName(authors.get(i).getName());
            author.addNewsItem(newsItem);
            authorRepository.save(author);
        }
    }

    public void assignNewsItemToCategories(NewsItem newsItem, List<Category> categories) {
        for (int i = 0; i < categories.size(); i++) {
            Category category = categoryRepository.findByName(categories.get(i).getName());
            category.addNewsItem(newsItem);
            categoryRepository.save(category);
        }
    }
    
    public List<NewsItem> createOtherNewsList(NewsItem newsItem) {
        List<NewsItem> otherNews = new ArrayList<>();
        for (Category category : newsItem.getCategories()) {
            for (NewsItem otherNewsItem : category.getNews()) {
                if(otherNewsItem.getId()!=newsItem.getId()) {
                    otherNews.add(otherNewsItem);
                }
            }
        }    
        return otherNews;
    }
}
