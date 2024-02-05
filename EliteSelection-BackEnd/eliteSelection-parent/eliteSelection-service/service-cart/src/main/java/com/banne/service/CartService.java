package com.banne.service;

import org.example.model.entity.h5.CartInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> getCartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    void clearCart();

    List<CartInfo> getAllCkecked();
}
