package com.banne.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.product.Product;

@Mapper
public interface ProductMapper {
    Product getById(Long productId);
}
