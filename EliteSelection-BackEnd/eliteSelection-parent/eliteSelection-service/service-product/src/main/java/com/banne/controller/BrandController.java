package com.banne.controller;

import com.banne.service.BrandService;
import org.checkerframework.common.reflection.qual.GetClass;
import org.example.model.entity.product.Brand;
import org.example.model.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ClientBrandController")
@RequestMapping("/api/product/brand")
@SuppressWarnings({"unchecked", "rawtypes"})
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("findAll")
    // 查询所有商品信息
    public Result<List<Brand>> findAll(){
        List<Brand> brandList = brandService.findAll();
        return  Result.build(brandList, ResultCodeEnum.SUCCESS);
    }

}
