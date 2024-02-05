package com.banne.service;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.servers.Server;
import org.example.model.dto.h5.ProductSkuDto;
import org.example.model.entity.product.ProductSku;
import org.example.model.vo.h5.ProductItemVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductSku> findTop10ProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);
}
