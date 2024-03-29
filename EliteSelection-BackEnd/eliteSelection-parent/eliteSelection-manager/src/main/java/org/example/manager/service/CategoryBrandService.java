package org.example.manager.service;

import com.github.pagehelper.PageInfo;
import org.example.model.dto.product.CategoryBrandDto;
import org.example.model.entity.product.Brand;
import org.example.model.entity.product.Category;
import org.example.model.entity.product.CategoryBrand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryBrandService {
    PageInfo<CategoryBrand> findPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
