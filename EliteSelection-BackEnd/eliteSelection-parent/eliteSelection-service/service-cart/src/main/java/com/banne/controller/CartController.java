package com.banne.controller;

import com.banne.service.CartService;
import org.example.model.entity.h5.CartInfo;
import org.example.model.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ClientCartController")
@RequestMapping("/api/order/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId,
                            @PathVariable("skuNum") Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/cartList")
    public Result<List<CartInfo>> cartList() {
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart( @PathVariable(value = "skuId") Long skuId,
                             @PathVariable(value = "isChecked") Integer isChecked) {
        cartService.checkCart(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable(value = "isChecked") Integer isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/auth/clearCart")
    public Result clearCart(){
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 展示所有购物车中选中的商品信息
    @GetMapping(value = "/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked() {
        List<CartInfo> cartInfoList = cartService.getAllCkecked() ;
        return cartInfoList;
    }
}
