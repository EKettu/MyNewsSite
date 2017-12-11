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
        newsItem.setPicture(fileObject);

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
    public void testCreateAuthorList() {
        String[] authors = new String[3];

        authors[0] = "Nimi1";
        authors[1] = "Nimi2";
        authors[2] = "Nimi3";
        Author author1 = new Author("Nimi1");
        Author author2 = new Author("Nimi2");
        Author author3 = new Author("Nimi3");
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);

        assertEquals(authors[0], newsService.createAuthorList(authors).get(0).getName());
        assertEquals(3, newsService.createAuthorList(authors).size());
    }

    @Test
    public void testCreateCategoryList() {
        String[] categories = new String[3];

        categories[0] = "Avaruus";
        categories[1] = "Kasvit";
        categories[2] = "Kissat";
        Category category1 = new Category("Avaruus");
        Category category2 = new Category("Kasvit");
        Category category3 = new Category("Kissat");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);

        assertEquals(categories[0], newsService.createCategoryList(categories).get(0).getName());
        assertEquals(3, newsService.createCategoryList(categories).size());
    }

    @Test
    public void testAssignNewsItemToAuthors() throws IOException {
        Author author1 = authorRepository.findByName("Jokunen");
        Author author2 = authorRepository.findByName("Virtanen");
        Author author3 = new Author("Karvinen");
        List<Author> authors = newsItem.getAuthors();
//      
//        authors.add(author1);
//        authors.add(author2);
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);

        newsService.assignNewsItemToAuthors(newsItem, authors);

        assertTrue(authorRepository.findByName("Jokunen").getNews().contains(newsItem));
        assertTrue(authorRepository.findByName("Virtanen").getNews().contains(newsItem));
        assertFalse(author3.getNews().contains(newsItem));
    }

    @Test
    public void testAssignNewsItemToCategories() throws IOException {
        Category category1 = categoryRepository.findByName("Luonto");
        Category category2 = new Category("Kissat");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        newsService.assignNewsItemToCategories(newsItem, categories);
        assertTrue(categoryRepository.findByName("Luonto").getNews().contains(newsItem));
        assertFalse(categoryRepository.findByName("Kissat").getNews().contains(newsItem));
    }

//    @Test
//    public void testCreateOtherNewsList() throws IOException {
//        String content = UUID.randomUUID().toString().substring(0, 6);
//        MockMultipartFile file = new MockMultipartFile("file", "aarrggghh2.png", "image/png", content.getBytes());
//        NewsItem newsItem2 = new NewsItem();
//        newsItem2.setTitle("Uutinen2");
//        newsItem2.setIngress("Ingressi");
//        newsItem2.setNewsText("Tekstiä vähän.");
//        newsItem2.setNewsDate(LocalDate.now());
//        newsItem2.setNewsTime(LocalDateTime.now());
//        FileObject fileObject = new FileObject();
//        fileObject.setName(file.getOriginalFilename());
//        fileObject.setContentType(file.getContentType());
//        fileObject.setContentLength(file.getSize());
//        fileObject.setContent(file.getBytes());
//        fileRepository.save(fileObject);
//        newsItem2.setPicture(fileObject);
//
//        List<Author> authors = newsItem2.getAuthors();
//        Author author1 = authorRepository.findByName("Virtanen");
//        newsItem2.addAuthor(author1);
//        newsItem2.setAuthors(authors);
//
//        List<Category> categories = newsItem2.getCategories();
//        Category category1 = categoryRepository.findByName("Luonto");
//        System.out.println("Category1 on " + category1);
//        System.out.println("Tässä kohtaa Luonto kategorian news on " + category1.getNews());
//                categoryRepository.save(category1);
//        newsItem2.addCategory(category1);
//
//        newsItem2.setCategories(categories);
//
//
//        System.out.println("Kategoria nimeltä Luonto tässä kohtaa " + categoryRepository.findByName("Luonto"));
//        newsRepository.save(newsItem2);
//
//        System.out.println("newsRepossa on " + newsRepository.findAll());
//        System.out.println("NewsItem2 is " + newsItem2);
//
//        System.out.println("NewsItemin kategoriat ovat " + newsItem.getCategories());
//
//        newsService.assignNewsItemToCategories(newsItem2, categories);
//        newsService.assignNewsItemToCategories(newsItem, categories);
//        newsRepository.save(newsItem2);
//        newsRepository.save(newsItem);
//        categoryRepository.save(category1);
//
//        List<NewsItem> otherNews = newsService.createOtherNewsList(newsItem);
//        System.out.println("Other News sisältää " + otherNews);
//        assertTrue(newsService.createOtherNewsList(newsItem).contains(newsItem2));
//        assertFalse(newsService.createOtherNewsList(newsItem).contains(newsItem));
//
//    }

}
