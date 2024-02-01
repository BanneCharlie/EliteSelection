package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.product.ProductSpec;

import java.util.List;

@Mapper
public interface ProductSpecMapper {

    List<ProductSpec> findAll();

    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Long id);
}
