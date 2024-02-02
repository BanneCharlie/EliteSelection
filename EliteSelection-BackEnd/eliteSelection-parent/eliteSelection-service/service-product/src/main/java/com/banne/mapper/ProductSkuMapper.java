package com.banne.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.product.ProductSku;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findTop10ProductSkuBySale();
}
