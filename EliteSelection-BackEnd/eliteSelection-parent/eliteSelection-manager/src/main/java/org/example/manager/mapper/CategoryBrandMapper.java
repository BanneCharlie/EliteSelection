package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.dto.product.CategoryBrandDto;
import org.example.model.entity.product.CategoryBrand;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> findPage(CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Long id);
}
