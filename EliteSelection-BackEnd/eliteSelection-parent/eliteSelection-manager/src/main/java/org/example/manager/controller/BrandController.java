package org.example.manager.controller;

import com.github.pagehelper.PageInfo;
import org.example.log.OperatorType;
import org.example.log.annotation.Log;
import org.example.manager.service.BrandService;
import org.example.model.entity.product.Brand;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Log(title = "品牌列表",businessType = 0,operatorType = OperatorType.MANAGE)
    @GetMapping("/{page}/{limit}")
    public Result<PageInfo<Brand>> findByPage(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit){
        PageInfo<Brand> pageInfo = brandService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/findAll")
    public Result<List<Brand>> findAll(){
        List<Brand> brandList = brandService.findAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }


    @PostMapping("/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Brand brand) {
        brandService.updateById(brand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable("id") Integer id) {
        brandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS) ;
    }
}
