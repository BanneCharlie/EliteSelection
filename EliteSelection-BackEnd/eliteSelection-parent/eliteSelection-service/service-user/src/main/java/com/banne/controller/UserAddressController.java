package com.banne.controller;

import com.banne.service.UserAddressService;
import org.example.model.entity.user.UserAddress;
import org.example.model.vo.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("ClientUserAddressController")
@RequestMapping("/api/user/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/auth/findUserAddressList")
    public Result<List<UserAddress>> findUserAddressList() {
        List<UserAddress> list = userAddressService.findUserAddressList();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }
}
