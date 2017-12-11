package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * Class for Author objects
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Author extends AbstractPersistable<Long> {

    @Id
    private Long id;
    private String name;

    /**
     * List containing NewsItems of this Author
     */
    @ManyToMany(fetch = FetchType.EAGER)
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

    /**
     * Method to make sure that a single NewsItem cannot be added twice to the
     * same author
     *
     * @param newsItem
     */
    public void addNewsItem(NewsItem newsItem) {
        for (NewsItem newsItem2 : getNews()) {
            if (newsItem2.getId() == newsItem.getId()) {
                return;
            }
        }
        getNews().add(newsItem);
    }
}
