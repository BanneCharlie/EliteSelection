package com.banne.feign.cart;

import org.example.model.entity.h5.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("service-cart")
public interface CartFeignClient {
    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public abstract List<CartInfo> getAllCkecked() ;
}
