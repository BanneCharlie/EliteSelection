package org.example.manager.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.model.entity.product.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public interface CategoryService {

    void exportData(HttpServletResponse response);

    List<Category> findByParentId(Long id);

    void importData(MultipartFile file);
}
