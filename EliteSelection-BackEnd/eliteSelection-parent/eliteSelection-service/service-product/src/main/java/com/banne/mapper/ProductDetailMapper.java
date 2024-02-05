package com.banne.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.product.ProductDetails;

@Mapper
public interface ProductDetailMapper {
    ProductDetails getByProductId(Long productId);
}
