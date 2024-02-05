package com.banne.service.imp;

import com.alibaba.fastjson2.JSON;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import org.example.feign.product.ProductFeignClient;
import org.example.model.entity.h5.CartInfo;
import org.example.model.entity.product.ProductSku;
import org.example.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import  com.banne.service.CartService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    private String getCartKey(Long userId) {
        return "user:cart:" + userId;
    }

    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        // 1.当前状态为登录状态,获取当前登录用户的id(作为redis的hash类型的key值)
        Long userId = AuthContextUtil.getUserInfo().getId();

        // redis中存放购物车信息的值为 key:userId field:skuId value:sku信息
        String cartKey = getCartKey(userId);

        // 2.从redis中获取当前用户添加到购物车中的信息 TODO
        Object cartInfoObject = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        CartInfo cartInfo = null;
        // 判断redis中是否存在当前商品
        if (cartInfoObject != null){
            cartInfo = JSON.parseObject(cartInfoObject.toString(),CartInfo.class);
            // 更新购物车中的数量
            cartInfo.setSkuNum(cartInfo.getSkuNum()+skuNum);
            // 设置属性,购物车位添加状态
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }else{
            // 3.redis中不存在添加进的购物车信息,将商品添加到购物车中(添加到redis里面)
            // 通过远程调用实现: nacos + openFeign 根据skuId获取商品的sku信息
            cartInfo = new CartInfo();
            // 购物车数据是从商品详情得到 {skuInfo}
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        // 4.将商品的数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey,String.valueOf(skuId),JSON.toJSONString(cartInfo));

    }


    @Override
    public List<CartInfo> getCartList() {
        // 1.获取登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        // 2.从redis中获取到购物车的信息
        List<Object> cartInfoList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(cartInfoList)){
            // 通过Stream流将cartInfoList集合中的JSON格式的字符串数据转换为CartInfo类型的数据并且根据创建的时间属性进行排序,生成的数据List集合接收
            return cartInfoList.stream().map(cartInfoObject -> {
                CartInfo cartInfo = JSON.parseObject(cartInfoObject.toString(),CartInfo.class);
                return cartInfo;
            }).sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).collect(Collectors.toList());
        }

        return new ArrayList<CartInfo>();
    }

    @Override
    public void deleteCart(Long skuId) {
        // 1.获取当前登录的用户id
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        // 2.通过key值将redis中对应的数据删除掉
        redisTemplate.opsForHash().delete(cartKey,String.valueOf(skuId));
    }

    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        // 1.获取当前登录的用户id
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        // 2.查询redis是否存在当前数据
        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));

        // 3.redis中存在,将对应的购物车数据状态进行修改
        if (hasKey){
            // 通过key值 filed值 获取到Redis中对应的hash类型数据
            String cartInfoJSON = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId)).toString();
            CartInfo cartInfo = JSON.parseObject(cartInfoJSON, CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey , String.valueOf(skuId) , JSON.toJSONString(cartInfo));
        }
    }

    @Override
    public void allCheckCart(Integer isChecked) {
        // 获取当前登录的用户数据
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        // 获取所有的购物项数据
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream().map(cartInfoJSON -> {
                CartInfo cartInfo = JSON.parseObject(cartInfoJSON.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
                return cartInfo ;
            }).forEach(cartInfo -> redisTemplate.opsForHash().put(cartKey , String.valueOf(cartInfo.getSkuId()) , JSON.toJSONString(cartInfo)));

        }
    }

    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllCkecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);

        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);       // 获取所有的购物项数据
        if(!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> cartInfoList = objectList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .collect(Collectors.toList());
            return cartInfoList ;
        }
        return new ArrayList<>() ;
    }
}
