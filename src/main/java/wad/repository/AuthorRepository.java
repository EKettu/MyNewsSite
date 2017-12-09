package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>  {
    
    Author findByName(String name);
}
