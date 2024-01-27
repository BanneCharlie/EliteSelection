package org.example.manager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileUploadService {
    String fileUpload(MultipartFile multipartFile);
}
