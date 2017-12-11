package wad.domain;

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
public class TestAuthor {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    NewsService newsService;

    @Autowired
    FileObjectRepository fileRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private NewsItem newsItem;

    @Before
    public void setUp() throws IOException {
        authorRepository.deleteAll();
        categoryRepository.deleteAll();
        newsRepository.deleteAll();
        String content = UUID.randomUUID().toString().substring(0, 6);
        MockMultipartFile file = new MockMultipartFile("file", "aarrggghh.jpg", "image/jpg", content.getBytes());
        newsItem = new NewsItem();
        newsItem.setTitle("Uutinen1");
        newsItem.setIngress("Ingressi");
        newsItem.setNewsText("Tekstiä vähän.");
        newsItem.setNewsDate(LocalDate.now());
        newsItem.setNewsTime(LocalDateTime.now());
        FileObject fileObject = new FileObject();
//        fileObject.setName(file.getOriginalFilename());
//        fileObject.setContentType(file.getContentType());
//        fileObject.setContentLength(file.getSize());
        fileObject.setContent(file.getBytes());
        fileRepository.save(fileObject);
        //  newsItem.setPicture(fileObject);

        List<Author> authors = newsItem.getAuthors();
        Author author1 = new Author("Jokunen");
        Author author2 = new Author("Virtanen");
        authorRepository.save(author1);
        authorRepository.save(author2);
        newsItem.addAuthor(author1);
        newsItem.addAuthor(author2);

        //newsItem.setAuthors(authors);
        List<Category> categories = newsItem.getCategories();
        Category category = new Category("Luonto");
        categoryRepository.save(category);
        newsItem.addCategory(category);

        newsItem.setCategories(categories);
        newsRepository.save(newsItem);
    }

    @Test
    public void testAddNewsItemToAuthor() {
        Author author = authorRepository.findByName("Jokunen");

        author.addNewsItem(newsItem);
        assertTrue(author.getNews().contains(newsItem));
    }

    @Test
    public void testCannotAddNewsItemTwice() {
        Author author = authorRepository.findByName("Jokunen");

        author.addNewsItem(newsItem);
        author.addNewsItem(newsItem);
        assertEquals(1, author.getNews().size());
    }

}
