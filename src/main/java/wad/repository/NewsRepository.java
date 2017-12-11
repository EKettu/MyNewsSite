package wad.repository;

import wad.domain.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsItem, Long> {

}
