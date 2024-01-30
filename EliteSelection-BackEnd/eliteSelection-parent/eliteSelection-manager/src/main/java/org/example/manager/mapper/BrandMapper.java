package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.product.Brand;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findByPage();

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Integer id);
}
