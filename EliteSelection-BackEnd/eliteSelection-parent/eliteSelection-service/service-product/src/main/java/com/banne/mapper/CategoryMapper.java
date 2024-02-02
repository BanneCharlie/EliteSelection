package com.banne.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.product.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findOneCategory();

    List<Category> findCategoryTree();
}
