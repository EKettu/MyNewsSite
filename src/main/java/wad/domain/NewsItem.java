package wad.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class NewsItem extends AbstractPersistable<Long> {

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String ingress;

    @ElementCollection
    @CollectionTable(name = "writers")
    private List<String> writers;

    private String category;

    private LocalDate newsDate;

    public NewsItem() {
        this.newsDate = LocalDate.now();
    }

    public LocalDate getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(LocalDate newsDate) {
        this.newsDate = newsDate;
    }
//    
//    private FileObject picture;
//
//    public FileObject getPicture() {
//        return picture;
//    }
//
//    public void setPicture(FileObject picture) {
//        this.picture = picture;
//    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Id
    private Long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIngress() {
        return ingress;
    }

    public void setIngress(String ingress) {
        this.ingress = ingress;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method to transfer writers from a list into a String
     *
     * @return writers as a String
     */
    public String authorsToString() {
        String output = "";
        int length = this.writers.size();

        if (length == 1) {
            output = this.writers.get(0);
        }

        if (length == 2) {
            output = this.writers.get(0) + " and " + this.writers.get(1);
        }

        if (length > 2) {
            for (int i = 0; i < length - 1; i++) {
                output = output + this.writers.get(i) + ", ";
            }
            output = output + "and " + this.writers.get(length - 1);
        }

        return output;
    }

}
