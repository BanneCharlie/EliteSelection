package com.banne.service;

import org.example.model.entity.product.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> findOneCategory();

    List<Category> findCategoryTree();
}
