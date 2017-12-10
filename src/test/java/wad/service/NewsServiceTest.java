package wad.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import wad.domain.Author;
import wad.domain.Category;
import wad.domain.FileObject;
import wad.domain.NewsItem;
import wad.repository.AuthorRepository;
import wad.repository.CategoryRepository;
import wad.repository.FileObjectRepository;
import wad.repository.NewsRepository;
import wad.service.NewsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewsServiceTest {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    FileObjectRepository fileRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    NewsService newsService;

    private NewsItem newsItem;

    @Before
    public void setUp() throws IOException {
        String content = UUID.randomUUID().toString().substring(0, 6);
        MockMultipartFile file = new MockMultipartFile("file", "aarrggghh.gif", "image/gif", content.getBytes());
        newsItem = new NewsItem();
        newsItem.setTitle("Uutinen1");
        newsItem.setIngress("Ingressi");
        newsItem.setNewsText("Tekstiä vähän.");
        newsItem.setNewsDate(LocalDate.now());
        newsItem.setNewsTime(LocalDateTime.now());
        FileObject fileObject = new FileObject();
        fileObject.setName(file.getOriginalFilename());
        fileObject.setContentType(file.getContentType());
        fileObject.setContentLength(file.getSize());
        fileObject.setContent(file.getBytes());
        fileRepository.save(fileObject);
        newsItem.setPicture(fileObject);

        List<Author> authors = new ArrayList<>();
        authors.add(new Author("Jokunen"));
        authors.add(new Author("Virtanen"));
        newsItem.setAuthors(authors);

        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Luonto"));
        newsItem.setCategories(categories);
        newsRepository.save(newsItem);
    }

    @Test
    public void testCreateAuthorList() {
        String[] authors = new String[3];

        authors[0] = "Virtanen";
        authors[1] = "Jokunen";
        authors[2] = "Karvinen";

        assertEquals(authors[0], newsService.createAuthorList(authors).get(0).getName());
        assertEquals(3, newsService.createAuthorList(authors).size());
    }

    @Test
    public void testCreateCategoryList() {
        String[] categories = new String[3];

        categories[0] = "Luonto";
        categories[1] = "Kasvit";
        categories[2] = "Kissat";

        assertEquals(categories[0], newsService.createCategoryList(categories).get(0).getName());
        assertEquals(3, newsService.createCategoryList(categories).size());
    }

    @Test
    public void testAssignNewsItemToAuthors() throws IOException {
        Author author1 = new Author("Jokunen");
        Author author2 = new Author("Virtanen");
        Author author3 = new Author("Karvinen");
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);

        newsService.assignNewsItemToAuthors(newsItem, authors);
        assertTrue(authorRepository.findByName("Jokunen").getNews().contains(newsItem));
        assertTrue(authorRepository.findByName("Virtanen").getNews().contains(newsItem));
        assertFalse(authorRepository.findByName("Karvinen").getNews().contains(newsItem));
    }

    @Test
    public void testAssignNewsItemToCategories() throws IOException {
        Category category1 = new Category("Luonto");
        Category category2 = new Category("Kissat");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        newsService.assignNewsItemToCategories(newsItem, categories);
        assertTrue(categoryRepository.findByName("Luonto").getNews().contains(newsItem));
        assertFalse(categoryRepository.findByName("Kissat").getNews().contains(newsItem));
    }

}
