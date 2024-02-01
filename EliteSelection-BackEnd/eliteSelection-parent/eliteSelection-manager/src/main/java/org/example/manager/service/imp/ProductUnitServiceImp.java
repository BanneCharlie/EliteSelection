package org.example.manager.service.imp;

import org.example.manager.mapper.ProductUnitMapper;
import org.example.manager.service.ProductUnitService;
import org.example.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImp implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper;

    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll() ;
    }
}
