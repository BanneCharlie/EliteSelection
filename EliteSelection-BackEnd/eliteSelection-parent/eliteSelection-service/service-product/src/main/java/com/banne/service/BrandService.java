package com.banne.service;

import org.example.model.entity.product.Brand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    List<Brand> findAll();
}
