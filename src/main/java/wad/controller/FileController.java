package wad.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.repository.NewsRepository;

/**
 * Class for managing picture file display
 *
 */
@Controller
public class FileController {

    /**
     * A repository for NewsItem objects
     */
    @Autowired
    private NewsRepository newsRepository;

    /**
     * Method for showing a picture file of an individual news item
     * @param newsItemId Long, id of an individual NewsItem object
     * @return byte[] containing the picture file
     */
    @GetMapping(path = "/newsItem/{newsItemId}/content", produces = "image/jpg")
    @ResponseBody
    public byte[] get(@PathVariable Long newsItemId) {
        return newsRepository.getOne(newsItemId).getPicture().getContent();
     //   return null;
    }

}
