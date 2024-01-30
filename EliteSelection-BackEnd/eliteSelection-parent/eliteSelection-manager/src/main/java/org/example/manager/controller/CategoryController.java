package org.example.manager.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.common.reflection.qual.GetClass;
import org.example.manager.service.CategoryService;
import org.example.model.entity.product.Category;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /** 查询列表
     *
     * @param id
     * @return
     */
    @GetMapping("/findByParentId/{id}")
    public Result<List<Category>>  findByParentId(@PathVariable("id") Long id) {
        List<Category> categoryList = categoryService.findByParentId(id);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    // 导出列表数据
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    // 导入数据
    @PostMapping("/importData")
    public Result importData(MultipartFile file) {
        categoryService.importData(file);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
