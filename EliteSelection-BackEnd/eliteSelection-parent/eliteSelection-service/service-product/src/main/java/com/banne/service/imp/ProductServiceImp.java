package com.banne.service.imp;

import com.alibaba.fastjson.JSON;
import com.banne.mapper.ProductDetailMapper;
import com.banne.mapper.ProductMapper;
import com.banne.mapper.ProductSkuMapper;
import com.banne.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.model.dto.h5.ProductSkuDto;
import org.example.model.entity.product.Product;
import org.example.model.entity.product.ProductDetails;
import org.example.model.entity.product.ProductSku;
import org.example.model.vo.h5.ProductItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CrossOrigin
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Override
    public List<ProductSku> findTop10ProductSkuBySale() {
        return productSkuMapper.findTop10ProductSkuBySale();
    }

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page, limit);
        List<ProductSku> list = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(list);
    }

    @Override
    public ProductItemVo item(Long skuId) {
        // 1.获取当前商品的sku
        ProductSku productSku = productSkuMapper.getBySkuId(skuId);

        // 2.根据sku获取商品id
        Long productId = productSku.getProductId();

        // 3.根据商品id获取当前商品基本信息
        Product product = productMapper.getById(productId);

        // 4.建立sku规格和skuId信息规格
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productId);
        //建立sku规格与skuId对应关系
        Map<String,Object> skuSpecValueMap = new HashMap<>();
        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(), item.getId());
        });
        // 5.获取当前商品的详细信息
        ProductDetails productDetail  = productDetailMapper.getByProductId(productId);

        // 6.封装返回的商品信息
        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetail.getImageUrls().split(",")));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        return productItemVo;
    }

    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productSkuMapper.getBySkuId(skuId);
    }
}
