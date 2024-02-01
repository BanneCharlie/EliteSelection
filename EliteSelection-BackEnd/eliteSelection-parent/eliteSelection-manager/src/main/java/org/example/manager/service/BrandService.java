package org.example.manager.service;

import com.github.pagehelper.PageInfo;
import org.example.model.entity.product.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void updateById(Brand brand);

    void deleteById(Integer id);

    List<Brand> findAll();
}
