package com.banne.service.imp;

import com.banne.mapper.ProductSkuMapper;
import com.banne.service.ProductService;
import org.example.model.entity.product.ProductSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
@CrossOrigin
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Override
    public List<ProductSku> findTop10ProductSkuBySale() {
        return productSkuMapper.findTop10ProductSkuBySale();
    }
}
