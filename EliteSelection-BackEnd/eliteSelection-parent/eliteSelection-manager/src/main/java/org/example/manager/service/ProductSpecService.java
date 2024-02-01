package org.example.manager.service;

import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.servers.Server;
import org.example.model.entity.product.ProductSpec;
import org.springframework.stereotype.Service;

@Service
public interface ProductSpecService {
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);

    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Long id);
}
