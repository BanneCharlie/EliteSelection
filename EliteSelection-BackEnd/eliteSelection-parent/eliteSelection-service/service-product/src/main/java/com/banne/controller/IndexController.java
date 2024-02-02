package com.banne.controller;

import com.banne.service.CategoryService;
import com.banne.service.ProductService;
import org.example.model.entity.product.Category;
import org.example.model.entity.product.ProductSku;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.common.Result;
import org.example.model.vo.h5.IndexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ClientIndexController")
@RequestMapping(value="/api/product/index")
@SuppressWarnings({"unchecked", "rawtypes"})
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping()
    public Result findData(){
        // 1.获取一级标题的分类信息
        List<Category> categoryList = categoryService.findOneCategory();
        // 2.根据销量进行降序排序,获取前10位以上架的商品信息
        List<ProductSku> productSkuList = productService.findTop10ProductSkuBySale();
        // 3.封装成indexVo对象,返回前端
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);

        return Result.build(indexVo,ResultCodeEnum.SUCCESS);
    }
}
