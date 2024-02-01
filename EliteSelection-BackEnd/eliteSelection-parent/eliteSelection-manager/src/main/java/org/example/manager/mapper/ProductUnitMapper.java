package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.model.entity.base.ProductUnit;

import java.util.List;

@Mapper
public interface ProductUnitMapper {
    List<ProductUnit> findAll();
}
