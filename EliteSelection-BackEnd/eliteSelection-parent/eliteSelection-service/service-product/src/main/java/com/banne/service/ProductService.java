package com.banne.service;

import io.swagger.v3.oas.annotations.servers.Server;
import org.example.model.entity.product.ProductSku;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductSku> findTop10ProductSkuBySale();
}
