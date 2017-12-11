package wad.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class NewsItem extends AbstractPersistable<Long> {
    
    @Column
    private String title;
    
    @Column(length = 3000)
    private String newsText;
    
    @Column(length = 500)
    private String ingress;
//
//    @ElementCollection
//    @CollectionTable(name = "writers")
//    private List<String> writers;

    @OneToOne(fetch = FetchType.EAGER)
    private FileObject picture;
    
    @ManyToMany(mappedBy = "news")
    private List<Category> categories;
    
    @ManyToMany(mappedBy = "news")
    private List<Author> authors;
    
    private LocalDateTime newsTime;
    
    private LocalDate newsDate;
    
    @Id
    private Long id;
    
    public NewsItem() {
        this.newsDate = LocalDate.now();
        this.newsTime = LocalDateTime.now();
    }
    
    public List<Author> getAuthors() {
        if(this.authors==null) {
            this.authors = new ArrayList<>();
        }
        return this.authors;
    }
    
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    public void addAuthor(Author author) {
        for (Author author2 : getAuthors()) {
            if (author2.getId() == author.getId()) {
                return;
            }
        }
        getAuthors().add(author);
    }
    
    public List<Category> getCategories() {
        if(this.categories== null) {
            this.categories = new ArrayList<>();
        }
        return this.categories;
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    public void addCategory(Category category) {
        for (Category category2 : getCategories()) {
            if (category2.getId() == category.getId()) {
                return;
            }
        }
        getCategories().add(category);
    }
    
    public LocalDateTime getNewsTime() {
        return newsTime;
    }
    
    public void setNewsTime(LocalDateTime newsTime) {
        this.newsTime = newsTime;
    }
    
    public LocalDate getNewsDate() {
        return newsDate;
    }
    
    public void setNewsDate(LocalDate newsDate) {
        this.newsDate = newsDate;
    }
    
    public FileObject getPicture() {
        return picture;
    }
    
    public void setPicture(FileObject picture) {
        this.picture = picture;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getNewsText() {
        return newsText;
    }
    
    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }
    
    public String getIngress() {
        return ingress;
    }
    
    public void setIngress(String ingress) {
        this.ingress = ingress;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
}
