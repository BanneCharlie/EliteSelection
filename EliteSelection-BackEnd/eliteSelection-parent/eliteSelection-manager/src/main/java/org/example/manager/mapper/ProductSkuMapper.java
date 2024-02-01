package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.product.ProductSku;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    void save(ProductSku productSku);

    List<ProductSku> selectByProductId(Long id);

    void updateById(ProductSku productSku);

    void deleteByProductId(Long id);
}
