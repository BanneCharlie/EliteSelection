package org.example.manager.mapper;

import org.example.model.entity.product.*;
import org.apache.ibatis.annotations.Mapper;
import org.example.model.dto.product.ProductDto;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findByPage(ProductDto productDto);

    void save(Product product);

    Product selectById(Long id);

    void updateById(Product product);

    void deleteById(Long id);
}
