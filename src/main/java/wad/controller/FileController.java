package wad.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.repository.NewsRepository;

@Controller
public class FileController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping(path = "/newsItem/{newsItemId}/content", produces = "image/gif")
    @ResponseBody
    public byte[] get(@PathVariable Long newsItemId) {
        return newsRepository.getOne(newsItemId).getPicture().getContent();
    }

}
