package wad.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import wad.domain.FileObject;
import wad.repository.FileObjectRepository;
import wad.repository.NewsRepository;

@Controller
public class FileController {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    FileObjectRepository fileRepository;

    @GetMapping(path = "/newsItem/{newsItemId}/content", produces = "image/gif")
    @ResponseBody
    public byte[] get(@PathVariable Long newsItemId) {
        return newsRepository.getOne(newsItemId).getPicture().getContent();
    }

}
