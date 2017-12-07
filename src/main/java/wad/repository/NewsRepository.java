package wad.repository;

import wad.domain.Newsitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<Newsitem, Long> {
    
}
