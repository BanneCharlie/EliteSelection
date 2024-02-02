package com.banne.service.imp;

import com.banne.mapper.CategoryMapper;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import org.example.model.entity.product.Category;
import com.banne.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findOneCategory() {
        return categoryMapper.findOneCategory();
    }

    @Override
    public List<Category> findCategoryTree() {
        // 1.获取category表中的所有数据,将获取到的数据进行封装
        List<Category> categoryList = categoryMapper.findCategoryTree();
        // 2.处理category表中的第一级分类  (通过Stream流 和 Lambda表达式进行实现)
        List<Category> oneCategoryList = categoryList.stream()
                .filter(item -> item.getParentId().longValue() == 0)
                .collect(Collectors.toList());

        // 3.处理category表中的第二级分类
        if(!CollectionUtils.isEmpty(oneCategoryList)) {
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = categoryList.stream()
                        .filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue())
                        .collect(Collectors.toList());

                oneCategory.setChildren(twoCategoryList);
                // 4.处理category表中的第三级分类
                if(!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        List<Category> threeCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }
        // 5.返回处理完成的数据
        return  oneCategoryList;
    }
}
