package org.example.manager.service;

import org.example.model.entity.base.ProductUnit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface ProductUnitService {
    List<ProductUnit> findAll();
}
