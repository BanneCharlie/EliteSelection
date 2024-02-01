package org.example.manager.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.manager.mapper.BrandMapper;
import org.example.manager.service.BrandService;
import org.example.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImp implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        // 1.初始化分页
        PageHelper.startPage(page, limit);
        // 2.执行查询
        List<Brand> brandList = brandMapper.findByPage();

        return new PageInfo<>(brandList);
    }

    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    @Override
    public void updateById(Brand brand) {
        brandMapper.updateById(brand);
    }

    @Override
    public void deleteById(Integer id) {
        brandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll() ;
    }
}
