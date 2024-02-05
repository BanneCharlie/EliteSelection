package com.banne.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.dto.h5.ProductSkuDto;
import org.example.model.entity.product.ProductSku;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findTop10ProductSkuBySale();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    ProductSku getBySkuId(Long skuId);

    List<ProductSku> findByProductId(Long productId);
}
