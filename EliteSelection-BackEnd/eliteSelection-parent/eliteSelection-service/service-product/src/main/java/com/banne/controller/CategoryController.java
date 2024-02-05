package com.banne.controller;

import com.banne.service.CategoryService;
import org.example.model.entity.product.Category;
import org.example.model.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ClientCategoryController")
@RequestMapping("/api/product/category")
public class CategoryController {

    @Autowired
    private CategoryService  categoryService;

    @GetMapping("/findCategoryTree")
    public Result<List<Category>> findCategoryTree(){
        List<Category> list = categoryService.findCategoryTree();
        return Result.build(list,  ResultCodeEnum.SUCCESS);
    }


}
