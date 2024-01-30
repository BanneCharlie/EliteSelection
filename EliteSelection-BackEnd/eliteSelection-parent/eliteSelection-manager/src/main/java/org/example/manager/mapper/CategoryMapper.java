package org.example.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;
import org.example.model.entity.product.Category;
import org.example.model.vo.product.CategoryExcelVo;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectByParentId(Long id);

    List<Category> selectAll();

    void batchInsert(List cachedDataList);
}
