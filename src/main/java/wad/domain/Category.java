package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Category extends AbstractPersistable<Long> {

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<NewsItem> news;
    
    @Id
    private Long id;

    public Category(String name) {
        this.name = name;
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
            if (newsItem2.getId() == newsItem.getId()) {
                return;
            }
        }
        getNews().add(newsItem);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
