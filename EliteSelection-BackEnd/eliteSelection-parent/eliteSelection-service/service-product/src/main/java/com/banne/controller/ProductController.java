package com.banne.controller;

import com.banne.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.example.model.dto.h5.ProductSkuDto;
import org.example.model.entity.product.ProductSku;
import org.example.model.vo.common.*;
import org.example.model.vo.h5.ProductItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ClientProductController")
@RequestMapping("/api/product")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{page}/{limit}")
    public Result<PageInfo<ProductSku>> findByPage( @PathVariable Integer page, @PathVariable Integer limit,
                                                    ProductSkuDto productSkuDto) {
        PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    // 获取商品的详细信息
    @GetMapping("/item/{skuId}")
    public Result<ProductItemVo> item(@PathVariable Long skuId) {
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo , ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId){
        return productService.getBySkuId(skuId);
    }

}
