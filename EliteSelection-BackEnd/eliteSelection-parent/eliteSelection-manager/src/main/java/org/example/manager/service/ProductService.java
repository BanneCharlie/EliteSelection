package org.example.manager.service;

import com.github.pagehelper.PageInfo;
import org.example.model.entity.product.*;
import org.example.model.dto.product.ProductDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);
}
