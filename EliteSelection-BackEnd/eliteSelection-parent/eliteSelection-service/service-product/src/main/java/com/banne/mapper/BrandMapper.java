package com.banne.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.product.Brand;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findAll();
}
