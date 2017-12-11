package wad.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wad.domain.Author;
import wad.domain.Category;
import wad.domain.NewsItem;
import wad.repository.AuthorRepository;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;

/**
 * Class for managing different lists related to NewsItems
 *
 */
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Method that creates a list of authors based on a String [] of author
     * names
     *
     * @param authors String[], received as a parameter
     * @return an ArrayList of Author objects
     */
    public List<Author> createAuthorList(String[] authors) {
        List<Author> authorsList = new ArrayList<>();
        for (int i = 0; i < authors.length; i++) {
            Author author = authorRepository.findByName(authors[i]);
            authorsList.add(author);
            //  authorRepository.save(author);
        }
        return authorsList;
    }

    /**
     * Method that creates a list of categories based on a String [] of category
     * names
     *
     * @param categories String[], received as a parameter
     * @return an ArrayList of Category objects
     */
    public List<Category> createCategoryList(String[] categories) {
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i < categories.length; i++) {
            Category category = categoryRepository.findByName(categories[i]);
            categoryList.add(category);
            //  categoryRepository.save(category);
        }
        return categoryList;
    }

    /**
     * Method for assigning a NewsItem to a list of Author objects
     *
     * @param newsItem
     * @param authors
     */
    public void assignNewsItemToAuthors(NewsItem newsItem, List<Author> authors) {
        for (int i = 0; i < authors.size(); i++) {
            Author author = authorRepository.findByName(authors.get(i).getName());
            author.addNewsItem(newsItem);
            authorRepository.save(author);
        }
    }

    /**
     * Method for assigning a NewsItem to a list of Category objects
     *
     * @param newsItem
     * @param categories
     */
    public void assignNewsItemToCategories(NewsItem newsItem, List<Category> categories) {
        for (int i = 0; i < categories.size(); i++) {
            Category category = categoryRepository.findByName(categories.get(i).getName());
            category.addNewsItem(newsItem);
            categoryRepository.save(category);
        }
    }

    /**
     * Creates a list of NewsItems that are in the same categories as the
     * NewsItem received as a parameter, but this list doesn't contain the given
     * NewsItem
     *
     * @param newsItem
     * @return
     */
    public List<NewsItem> createOtherNewsList(NewsItem newsItem) {
        List<NewsItem> otherNews = new ArrayList<>();
        for (Category category : newsItem.getCategories()) {
            for (NewsItem otherNewsItem : category.getNews()) {
                if (otherNewsItem.getId() != newsItem.getId()) {
                    otherNews.add(otherNewsItem);
                }
            }
        }
        return otherNews;
    }

    /**
     * Method for getting a list of the five newest NewsItems
     *
     * @return
     */
    public List<NewsItem> getFiveNewestNews() {
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "newsTime");
        Page<NewsItem> selectedNews = newsRepository.findAll(pageable);
        List<NewsItem> fiveNewest = selectedNews.getContent();

        return fiveNewest;
    }

    /**
     * Method for getting a list of all NewsItems that are not among the five
     * newest ones
     *
     * @param fiveNewest
     * @return
     */
    public List<NewsItem> getOlderNews(List<NewsItem> fiveNewest) {
        int size = (int) newsRepository.count();
        if (size == 0) {
            size = 1;
        }

        Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC, "newsTime");
        Page<NewsItem> allNews = newsRepository.findAll(pageable);
        List<NewsItem> olderNews = new ArrayList<>();

        for (NewsItem newsItem : allNews) {
            if (!fiveNewest.contains(newsItem)) {
                olderNews.add(newsItem);
            }
        }
        return olderNews;
    }
}
