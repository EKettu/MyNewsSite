
package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.repository.NewsRepository;

@Service
public class NewsService {
    
     @Autowired
     private NewsRepository newsRepository;  
}
