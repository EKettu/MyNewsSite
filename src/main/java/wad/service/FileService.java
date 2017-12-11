package wad.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.FileObject;
import wad.repository.FileObjectRepository;

/**
 * Class for managing the creation of FileObjects
 *
 */
@Service
public class FileService {

    @Autowired
    FileObjectRepository fileRepository;

    /**
     * Method for creating a FileObject from a MultipartFile
     *
     * @param file MultipartFile, received as a parameter
     * @return FileObject ("image/jpg")
     * @throws IOException
     */
    public FileObject createFile(MultipartFile file) throws IOException {
        FileObject fileObject = new FileObject();
        //  if (file.getContentType().equals("image/jpg") || file.getContentType().equals("image/jpeg") ) {
//            fileObject.setName(file.getOriginalFilename());
//            fileObject.setContentType(file.getContentType());
//            fileObject.setContentLength(file.getSize());
        fileObject.setContent(file.getBytes());
        fileRepository.save(fileObject);
        //   }
        return fileObject;
    }
}
