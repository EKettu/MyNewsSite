package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Author extends AbstractPersistable<Long> {

    @Id
    private Long id;
    private String name;

    @ManyToMany
    private List<NewsItem> news;

    public Author(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<NewsItem> getNews() {
       if (this.news == null) {
           this.news = new ArrayList<NewsItem>();
       }
       return this.news;
    }
    
    public void setNews(List<NewsItem> news) {
        this.news = news;
    }
    
    public void addNewsItem(NewsItem newsItem) {
        for (NewsItem newsItem2 : getNews()) {
            if(newsItem2.getId()== newsItem.getId()) {
                return;
            }
        }
        getNews().add(newsItem);
    }
}
